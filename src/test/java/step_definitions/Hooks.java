package step_definitions;

import constants.TestDataConstants;
import helpers.datetime.DateTimeGenerator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.SkipException;
import pageobjects.DriverFactory;
import pageobjects.DriverManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constants.TestContextConstants.TEXT_CONTEXT_TAGS_NAME;
@Slf4j
public class Hooks extends BaseSetup implements ConcurrentEventListener {
    @Before("@api or @flow")
    public void setEnvParameters(Scenario scenario) {
        testContext().set(TEXT_CONTEXT_TAGS_NAME, scenario.getSourceTagNames());
    }

    @Before("@ui")
    public void beforeUI(Scenario scenario) throws Exception {
        DriverFactory.initDriver(scenario);

    }

    @After("@ui")
    public void afterUI(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            takeUIScreenshot();
        }
        DriverManager.getDriver().quit();
    }

    @Before
    public void beforeEach(Scenario scenario){
        beforeEachScenario(scenario);
    }

    @After
    public void afterEach(Scenario scenario) {
        afterEachScenario(scenario);
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunStarted.class, beforeAll);
        eventPublisher.registerHandlerFor(TestRunFinished.class, afterAll);
    }

    public synchronized void beforeAllTest() {
        loadEnvironmentData(System.getProperty("environment"));
        generateDataConfig().setGenerateConfig(TestDataConstants.GENERATE_DATA_CONFIG_PATH);
        cleanTestData();
    }

    public synchronized void afterEachScenario(Scenario scenario) {
        if(scenario.isFailed() || scenario.getStatus() == Status.SKIPPED) {
            List<Scenario> scenarioList = ScenarioList.getScenarioList();
            scenarioList.add(scenario);
        }
        ScenarioList.increaseTotalScenario();
    }

    public void beforeEachScenario(Scenario scenario) {
        if(isSkippedScenario(scenario)){
            throw new SkipException("Skipped scenario");
        }
    }

    private final EventHandler<TestRunStarted> beforeAll = event -> {
        beforeAllTest();
    };

    private final EventHandler<TestRunFinished> afterAll = event -> {
    };

    private void cleanTestData() {
    }

    private void takeUIScreenshot() throws IOException {
        TakesScreenshot scrShot = ((TakesScreenshot) DriverManager.getDriver());
        File screenshot = scrShot.getScreenshotAs(OutputType.FILE);
        String currentDate = DateTimeGenerator.generateCurrentDate("dd-MMM-YY");
        File destination = new File(String.format("reports/ %s/test-output/screenshots/%s", currentDate, screenshot.getName()));
        FileUtils.copyFile(screenshot, destination);
        addScreenShootToStep("Screenshot", "../screenshots/" + screenshot.getName());
    }
    private boolean isSkippedScenario(Scenario scenario){
        return scenario.getSourceTagNames().stream().anyMatch(tag -> tag.startsWith("@skip"));
    }

    public static class ScenarioList {
        private static List<Scenario> scenarioList;
        private static int totalScenario;

        public static int getTotalScenario() {
            return totalScenario;
        }

        public static void increaseTotalScenario() {
            totalScenario++;
        }

        public synchronized static List<Scenario> getScenarioList() {
            if (scenarioList == null) {
                scenarioList = new ArrayList<>();
            }
            return scenarioList;
        }
    }
}