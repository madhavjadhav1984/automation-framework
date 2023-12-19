package pages.outpath;

import common.BasePage;
import common.CustomDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static helpers.Utils.pause;

public class LoginPage extends BasePage {
    @FindBy(css = "input[placeholder='Mobile Number']")
    WebElement mobileNumber;
    @FindBy(css = "button[class='subbtn-continue']")
    WebElement continueBtn;

    public LoginPage(CustomDriver driver) {
        super(driver);
    }

    @Override
    public boolean verifyPageIsLoaded() {
        waitForPageToLoad();
        return true;
    }
    public void enterMobileNumber(final String mNumber)
    {
        getDriver().waitUntil().isClickable(mobileNumber).click();
        getDriver().waitUntil().isVisible(mobileNumber).sendKeys(mNumber);
    }

    public void clickOnContinue()
    {
        getDriver().waitUntil().isClickable(continueBtn).click();
    }

    public boolean isContinueBtnEnabled()
    {
        return continueBtn.isEnabled();
    }

}
