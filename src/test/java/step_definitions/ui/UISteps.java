package step_definitions.ui;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import pageobjects.upload.UploadPage;
import step_definitions.BaseSetup;

@Slf4j
public class UISteps extends BaseSetup {
    UploadPage uploadPage = new UploadPage();

    //region Driver steps
    @When("I click submit button")
    public void clickSubmitButton() {
        uploadPage.clickSubmitButton();
    }

    @Given("I open the upload page")
    public void iOpenTheUploadPage() {
        uploadPage.open();
    }

    @Given("I choose file {string} to upload")
    public void iChooseFileToUpload(String file) {
        uploadPage.chooseFile(file);
    }

    @When("I upload the file {string}")
    public void iSelectTheFileToUpload(String filePath) throws InterruptedException {
        uploadPage.uploadFile(filePath);
    }

    @Then("I expect the response message is displayed as {string}")
    public void iExpectRewardBehindScratchCardIsCorrect(String responseMessage) {
        uploadPage.verifyResponseMessage(responseMessage);
    }
}
