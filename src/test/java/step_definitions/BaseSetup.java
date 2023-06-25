package step_definitions;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import constants.UIConstants;
import helpers.TestContext;
import helpers.data.GenerateDataConfig;
import helpers.json.JsonConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static constants.TestDataConstants.ENVIRONMENT_PARAMETERS;
@Slf4j
public abstract class BaseSetup {
    private static Map<String, Object> environmentData;

    public static TestContext testContext() {
        return TestContext.CONTEXT;
    }

    public static GenerateDataConfig generateDataConfig() {
        return GenerateDataConfig.GENERATE_CONFIG;
    }

    public void addLogToExtentReport(String title, Object content) {
        String contentAsString = content.toString();
        if(contentAsString.contains("no-js")){
            String message = contentAsString
                    .substring(contentAsString.indexOf("|") + 1, contentAsString.indexOf("</title"));

            ExtentCucumberAdapter.addTestStepLog(String.format("  %s<pre>%s</pre>", title, message));
        } else {
            ExtentCucumberAdapter.addTestStepLog(String.format("  %s<pre>%s</pre>", title, content));
        }
    }

    public void addScreenShootToStep(String title, String imagePath) {
        //ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(imagePath, title);
        //[TODO] Not sure why this one not work with relative path. So I temp work around with adding by other way. Will back later
        addLogToExtentReport(title, String.format(UIConstants.EMBEDED_IMG_REPORT, imagePath));
    }

    public static void loadEnvironmentData(String environment) {
        environmentData = JsonConverter.convertJsonFileToHashMap(String.format(ENVIRONMENT_PARAMETERS, environment));
    }

    public static Map<String, Object> getEnvironmentData() {
        return environmentData;
    }
}
