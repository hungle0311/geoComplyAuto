package pageobjects;

import constants.UIConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
public abstract class BasePage {
    protected WebDriver webDriver;
    public String baseUrl;
    public WebDriverWait webDriverWait;
    public Actions action;
    public SoftAssert softAssert = new SoftAssert();
    public JavascriptExecutor js;


    public BasePage() {
        this.webDriver = DriverManager.getDriver();
        PageFactory.initElements(webDriver, this);
        setBaseUrl();
        this.webDriverWait = new WebDriverWait(this.webDriver,UIConstants.DEFAULT_WEB_WAIT_TIMEOUT);
    }

    public void waitForPageLoad() {
        this.webDriverWait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public WebElement waitUntilElementVisible(By locator) {
        return this.webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public abstract void open();

    public abstract void setBaseUrl();

    //region image
}
