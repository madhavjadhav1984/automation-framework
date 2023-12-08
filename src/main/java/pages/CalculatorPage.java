package pages;

import common.BasePage;
import common.CustomDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CalculatorPage extends BasePage {

    @FindBy(id = "button01")
    private WebElement one;
    @FindBy(id = "button02")
    private WebElement two;
    @FindBy(id = "button04")
    private WebElement four;
    @FindBy(id = "button06")
    private WebElement six;
    @FindBy(id = "buttonplus")
    private WebElement plus;
    @FindBy(id = "buttonmultiply")
    private WebElement multiply;
    @FindBy(id = "buttonequals")
    private WebElement equals;
    @FindBy(id = "calculated-display")
    private WebElement result;

    public CalculatorPage(CustomDriver driver) {
        super(driver);
    }

    @Override
    public boolean verifyPageIsLoaded() {
        waitForPageToLoad();
        return true;
    }

    public void additionOfTwoNum()
    {
        getDriver().waitUntil().isClickable(one).click();
        getDriver().waitUntil().isClickable(plus).click();
        getDriver().waitUntil().isClickable(two).click();
        getDriver().waitUntil().isClickable(equals).click();
    }

    public void multiplyTwoNumber()
    {
        getDriver().waitUntil().isClickable(four).click();
        getDriver().waitUntil().isClickable(multiply).click();
        getDriver().waitUntil().isClickable(six).click();
        getDriver().waitUntil().isClickable(equals).click();
    }

    public String getResult()
    {
        return getDriver().getTextOrValue(result);
    }
}
