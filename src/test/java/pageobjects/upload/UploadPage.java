package pageobjects.upload;

import helpers.map.MapHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.BasePage;
import step_definitions.BaseSetup;

public class UploadPage extends BasePage {

    public UploadPage() {
        super();
    }

    @Override
    public void open() {
        webDriver.get(this.baseUrl);
    }

    @Override
    public void setBaseUrl() {
        this.baseUrl = MapHelper.getNestedString(BaseSetup.getEnvironmentData(), "uploadURL");
    }

    @FindBy(id = "uploadfile_0")
    public WebElement uploadFile;

    @FindBy(name = "send")
    public WebElement submitFileBtn;

    @FindBy(id = "terms")
    public WebElement acceptTermCkb;

    @FindBy(id = "res")
    public WebElement responseMessageTxt;

    public void clickSubmitButton() {
        submitFileBtn.click();
    }

    public void chooseFile(String filePath) {
        uploadFile.sendKeys(filePath);
    }

    public void acceptServiceTerm() {
        acceptTermCkb.click();
    }

    public void uploadFile(String filePath) throws InterruptedException {
        chooseFile(filePath);
        acceptServiceTerm();
        clickSubmitButton();
    }

    public void verifyResponseMessage(String message){
        waitUploading();
        String responseMessage = responseMessageTxt.getText();
        softAssert.assertEquals(responseMessage, message);
        softAssert.assertAll();
    }

    public void waitUploading() {
        waitUntilElementVisible(By.xpath("//h3[@style='color: rgb(255, 255, 255); display: block;']"));
    }
}
