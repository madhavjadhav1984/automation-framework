package pages.outpath;

import common.BasePage;
import common.CustomDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPage extends BasePage {

    @FindBy(name = "Source")
    WebElement SourceLocation;

    @FindBy(css = "input[placeholder='Destination']")
    WebElement DestinationLocation;

    public LandingPage(CustomDriver driver) {
        super(driver);
    }

    @Override
    public boolean verifyPageIsLoaded() {
        waitForPageToLoad();
        return true;
    }

    public void enterSourceLocation(String SLocation){

        getDriver().waitUntil().isClickable(SourceLocation).click();
        getDriver().waitUntil().isVisible(SourceLocation).sendKeys(SLocation);

    }

    public void enterDestinationLocation(String DLocation){

        getDriver().waitUntil().isClickable(DestinationLocation).click();
        getDriver().waitUntil().isVisible(DestinationLocation).sendKeys(DLocation);
    }
}
