package scripts.ui;

import common.BaseTest;
import org.testng.annotations.Test;

import java.time.Duration;

import static helpers.Utils.pause;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WebCalculatorTest extends BaseTest {

    @Test
    public void testAdditionOfTwoNumbers(){
        getDriver().goTo("https://testpages.eviltester.com/styled/apps/calculator.html");
        assertTrue(getCalculatorPage().verifyPageIsLoaded(),
                "Page not loaded");
        getCalculatorPage().additionOfTwoNum();
        pause(Duration.ofSeconds(5));
        assertEquals(getCalculatorPage().getResult(),"3");
    }

    @Test
    public void testMultiplicationOfTwoNumber(){
        getDriver().goTo("https://testpages.eviltester.com/styled/apps/calculator.html");
        assertTrue(getCalculatorPage().verifyPageIsLoaded(),
                "Page not loaded");
        getCalculatorPage().multiplyTwoNumber();
        pause(Duration.ofSeconds(3));
        assertEquals(getCalculatorPage().getResult(),"24");

    }
}
