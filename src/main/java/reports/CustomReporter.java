package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.Objects;

public class CustomReporter {
    private static ExtentReports reports;
    static final String REPORT_NAME = "Execution Test Report";
    static final String REPORT_PATH = System.getProperty("user.dir") + "/target/";
    private static final ExtentSparkReporter defaultReporter = new ExtentSparkReporter(String.format("%s%s.html", REPORT_PATH, REPORT_NAME));

    private CustomReporter(){

    }

    private static void setReporter(){
        reports = new ExtentReports();
        reports.attachReporter(defaultReporter);
    }

    public static ExtentReports getReporter(){
        if(Objects.isNull(reports)) {
            setReporter();
        }
        return reports;
    }

    public static void setSystemInformation(final String key, final String value){
        getReporter().setSystemInfo(key, value);
    }


}
