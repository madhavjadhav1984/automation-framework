package common;

import common.enums.Browser;
import common.factories.PageFactory;
import org.openqa.selenium.devtools.DevTools;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import pages.CalculatorPage;
import reports.CustomListener;

import java.util.Objects;

import static common.Constants.BROWSER_DEFAULT;
import static common.factories.DriverFactory.*;

@Listeners(CustomListener.class)
public abstract class BaseTest {

    protected CustomDriver driver;
    protected DevTools devTools;
    private Browser browser;

    protected CustomDriver getDriver() {return getCurrentThreadDriver(); }

    private void registerPage(){
        PageFactory.registerPage(CalculatorPage.class, () -> new CalculatorPage(getDriver()));
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest(){
        APIManager.resetRESTState();
        driver = getFactoryDriver(getBrowserType());
        getDriver().waitUntil().DOMisLoaded();
        getDriver().maximizeWindow();
        getDriver().clearCookies();
        registerPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownDriver(){
        quitCurrentDriver();
        APIManager.resetRESTState();
    }

    public Browser getBrowserType(){
        if(Objects.isNull(browser)){
            final var property = System.getProperty("browser");
            browser = Objects.isNull(property)
                    ? BROWSER_DEFAULT
                    : Browser.fromValue(property);
        }
        return browser;
    }

    public CalculatorPage getCalculatorPage()
    {
        return PageFactory.getPage(CalculatorPage.class, getDriver());
    }


}
