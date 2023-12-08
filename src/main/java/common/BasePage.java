package common;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;
import reports.CustomListener;

@Listeners({CustomListener.class})
public abstract class BasePage {

    private final CustomDriver driver;

    public BasePage(final CustomDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver.nativeDriver(), this);
    }

    protected CustomDriver getDriver() {return driver;}

    public void waitForPageToLoad() {
        getDriver().waitUntil().allAjaxCallsComplete();
    }

    public abstract boolean verifyPageIsLoaded();
}
