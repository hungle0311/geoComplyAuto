package pageobjects;

import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
public class DriverFactory {
    public static void initDriver(Scenario scenario) throws Exception {
        try {
            Properties webdriverProperties = loadWebDriverProperties();
            String browser = System.getProperty("browser") == null ? webdriverProperties.getProperty("browser") : System.getProperty("browser");
            WebDriver webDriver = null;
            switch (browser) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", String.format(getDriverFolder(),"chromedriver"));
                    ChromeOptions chromeOptions = new ChromeOptions().addArguments(getArguments(webdriverProperties));
                    //Disable the notification "Chrome is being controlled by automated software"
                    chromeOptions.setExperimentalOption("useAutomationExtension", false);
                    chromeOptions.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
                    //Set chrome headless if any
                    if (webdriverProperties.getProperty("headless").equals("true")) chromeOptions.setHeadless(true);
                    //Set chrome responsive mode for mobile if any
                    if (scenario.getSourceTagNames().toString().contains("resolution-mobile")) {
                        Map<String, Object> deviceMetrics = new HashMap<>();
                        deviceMetrics.put("width", Integer.parseInt(webdriverProperties.getProperty("mobile-width")));
                        deviceMetrics.put("height", Integer.parseInt(webdriverProperties.getProperty("mobile-height")));
                        Map<String, Object> mobileEmulation = new HashMap<>();
                        mobileEmulation.put("deviceMetrics", deviceMetrics);
                        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                    }
                    webDriver = new ChromeDriver(chromeOptions);
                    break;
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", String.format(getDriverFolder(),"geckodriver"));
                    FirefoxOptions firefoxOptions = new FirefoxOptions().addArguments(getArguments(webdriverProperties));
                    if (webdriverProperties.getProperty("headless").equals("true")) firefoxOptions.setHeadless(true);
                    webDriver = new FirefoxDriver(firefoxOptions);
                    break;
            }
            setDesktopDimension(webDriver, webdriverProperties, scenario);
            DriverManager.setWebDriver(webDriver);
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw ex;
        }
    }

    private static List<String> getArguments(Properties webdriverProperties){
        String arguments =  webdriverProperties.getProperty("arguments");
         return Arrays.asList(arguments.split(","));
    }

    private static String getDriverFolder(){
        String operationSystemName = System.getProperty("os.name").toLowerCase();
        if(operationSystemName.contains("mac")) return "src/drivers/macos/%s";
        if(operationSystemName.contains("linux")) return "src/drivers/linux/%s";
        return "";
    }

    private static void setDesktopDimension(WebDriver webDriver, Properties webdriverProperties, Scenario scenario) {
        if(scenario.getSourceTagNames().toString().contains("resolution-desktop")){
            int width = Integer.parseInt(webdriverProperties.getProperty("desktop-width"));
            int height = Integer.parseInt(webdriverProperties.getProperty("desktop-height"));
            Dimension dimension = new Dimension(width, height);
            webDriver.manage().window().setSize(dimension);
        }
        if(scenario.getSourceTagNames().toString().contains("resolution-mobile")){
            int width = Integer.parseInt(webdriverProperties.getProperty("mobile-width"));
            int height = Integer.parseInt(webdriverProperties.getProperty("mobile-height"));
            Dimension dimension = new Dimension(width, height);
            webDriver.manage().window().setSize(dimension);
        }
    }

    private static Properties loadWebDriverProperties() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/webdriver.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        return properties;
    }

}
