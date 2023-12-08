package common;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import reports.CustomListener;

@Listeners(CustomListener.class)
public abstract class APIBaseTest {

    @BeforeMethod(alwaysRun= true)
    public void TestStart()
    {

    }
}
