package pages.outpath;

import common.BasePage;
import common.CustomDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static helpers.Utils.pause;

public class OTPPage extends BasePage {
    @FindBy(css = "[class='ng-otp-input-wrapper wrapper'] :nth-child(1)")
    WebElement input1;
    @FindBy(css = "[class='ng-otp-input-wrapper wrapper'] :nth-child(2)")
    WebElement input2;
    @FindBy(css = "[class='ng-otp-input-wrapper wrapper'] :nth-child(3)")
    WebElement input3;
    @FindBy(css = "[class='ng-otp-input-wrapper wrapper'] :nth-child(4)")
    WebElement input4;
    @FindBy(css = "button[class='subbtn-continue']")
    WebElement loginBtn;
    @FindBy(css = ".text-danger.errorotp")
    WebElement errorMsg;

    public OTPPage(CustomDriver driver) {
        super(driver);
    }

    @Override
    public boolean verifyPageIsLoaded() {
        waitForPageToLoad();
        return true;
    }

    public void enterOTP(String otp1, String otp2, String otp3, String otp4)
    {
        //input1.sendKeys("1");
        getDriver().waitUntil().isClickable(input1).click();
        getDriver().waitUntil().isVisible(input1).sendKeys(otp1);
        getDriver().waitUntil().isClickable(input2).click();
        getDriver().waitUntil().isVisible(input2).sendKeys(otp2);
        getDriver().waitUntil().isClickable(input3).click();
        getDriver().waitUntil().isVisible(input3).sendKeys(otp3);
        getDriver().waitUntil().isClickable(input4).click();
        getDriver().waitUntil().isVisible(input4).sendKeys(otp4);
        getDriver().waitUntil().isClickable(loginBtn).click();
    }

    public String getErrorMessage()
    {
        pause(Duration.ofSeconds(5));
        return errorMsg.getText();
    }

}
