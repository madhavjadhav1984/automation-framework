package reports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.Markup;

public class CustomExtentTest {

    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    private CustomExtentTest()
    {

    }
    public static ExtentTest getTest() {return extentTest.get();}

    public static void setTest(final ExtentTest test) {extentTest.set(test);}

    public static void removeTest() {extentTest.remove();}

    public static ExtentTest getLogger(){return getTest();}

    public static void logInfo(final String details) {getLogger().info(details);}
    public static void logInfo(final Markup details) {getLogger().info(details);}
    public static void logPass(final String details) {getLogger().pass(details);}
    public static void logPass(final Markup details) {getLogger().pass(details);}
    public static void logFail(final String details) {getLogger().fail(details);}
    public static void logFail(final Markup details) {getLogger().fail(details);}

}
