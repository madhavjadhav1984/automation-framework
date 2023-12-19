package scripts.ui.outpath;

import common.BaseTest;
import org.testng.annotations.Test;

import java.time.Duration;

import static helpers.Utils.pause;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @Test
    public void testIncorrectMobileNumber()
    {
        getDriver().goTo("https://outpath-web-test.outpathprod.com");
        assertTrue(getLoginPage().verifyPageIsLoaded(),
                "Login Page not loaded");
        getLoginPage().enterMobileNumber("0000000000");
        assertTrue(!getLoginPage().isContinueBtnEnabled());
    }

    @Test
    public void testInvalidOTP()
    {
        getDriver().goTo("https://outpath-web-test.outpathprod.com");
        assertTrue(getLoginPage().verifyPageIsLoaded(),
                "Login page not loaded");
        getLoginPage().enterMobileNumber("9800457834");
        getLoginPage().clickOnContinue();

        assertTrue(getOTPPage().verifyPageIsLoaded(),
                "Page not loaded");
        getOTPPage().enterOTP("1","2","3","4");
        assertEquals(getOTPPage().getErrorMessage(),
                "OTP verification failed, Please enter correct OTP",
                "Incorrect error msg");
    }

    @Test
    public void testLogin()
    {
        getDriver().goTo("https://outpath-web-test.outpathprod.com");
        assertTrue(getLoginPage().verifyPageIsLoaded(),
                "Login Page not loaded");

        getLoginPage().enterMobileNumber("9800457834");
        getLoginPage().clickOnContinue();

        assertTrue(getOTPPage().verifyPageIsLoaded(),
                "OTP Page not loaded");
        getOTPPage().enterOTP("1","2","3","4");

        assertTrue(getLandingPage().verifyPageIsLoaded(),"Landing Page not Loaded");
        getLandingPage().enterSourceLocation("T Nagar, Chennai, Tamil Nadu, India");
        getLandingPage().enterDestinationLocation("Velachery, Chennai, Tamil Nadu, India");

        pause(Duration.ofSeconds(15));




    }

}
