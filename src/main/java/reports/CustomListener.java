package reports;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import common.CustomDriver;
import common.factories.DriverFactory;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.List;

import static reports.CustomExtentTest.getLogger;
import static reports.CustomExtentTest.logFail;

public class CustomListener<D extends CustomDriver> extends DriverFactory implements ITestListener {

    @Override
    public void onStart(final ITestContext context) {ITestListener.super.onStart(context);}

    @Override
    public void onTestStart(final ITestResult result)
    {
        CustomExtentTest.setTest(CustomReporter.getReporter().createTest(result.getMethod().getMethodName(), result.getMethod().getDescription()));
    }

    @Override
    public void onTestSuccess(final ITestResult result) {CustomExtentTest.removeTest();}

    @Override
    public void onTestFailure(final ITestResult result){
        final String message = String.format("%s%n%s",
                result.getThrowable().toString(),
                String.join("\n",getStackTraceAsList(result)));
        try
        {
            final String base64Screenshot = getCurrentThreadDriver().getScreenShotAs(OutputType.BASE64);
            getLogger().fail(MarkupHelper.createCodeBlock(message, CodeLanguage.XML).getMarkup(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } catch (Exception e){
            logFail("Failed to take screenshot for failed test" + e.getMessage());
        }

        CustomExtentTest.removeTest();
    }

    @Override
    public void onTestSkipped(final ITestResult result) {CustomExtentTest.removeTest();}

    public void onFinish(final ITestContext context) {CustomReporter.getReporter().flush();}

    protected List<String> getStackTraceAsList(ITestResult result){
        return Arrays.stream(result.getThrowable()
                .getStackTrace())
                .map(StackTraceElement::toString)
                .toList();
    }
}
