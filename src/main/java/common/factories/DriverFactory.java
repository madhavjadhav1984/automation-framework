package common.factories;

import common.CustomDriver;
import common.enums.Browser;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DriverFactory {
    private static final Map<Long,ThreadLocal<CustomDriver>> driverPool = new ConcurrentHashMap<>();

    public static CustomDriver getFactoryDriver(final Browser browser)
    {
        final var threadId = Thread.currentThread().getId();
        final CustomDriver cd;
        final var privateMode = Boolean.parseBoolean(System.getProperty("private"));
        final var headlessMode = Boolean.parseBoolean(System.getProperty("headless"));

        switch (browser){
            case CHROME -> {
                System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                if(headlessMode){
                    chromeOptions.addArguments("--headless");
                }
                if(privateMode){
                    chromeOptions.addArguments("--incognito");
                }
                cd = new CustomDriver(new ChromeDriver(chromeOptions));
            }
            case FIREFOX -> {
                var firfoxOptions = new FirefoxOptions();
                if(headlessMode){
                    firfoxOptions.addArguments("--headless");
                }
                if(privateMode){
                    firfoxOptions.addArguments("--incognito");
                }
                cd = new CustomDriver(new FirefoxDriver(firfoxOptions));
            }
            case EDGE -> {
                EdgeOptions edgeOptions = new EdgeOptions();
                if(headlessMode){
                    edgeOptions.addArguments("--headless");
                }
                if(privateMode){
                    edgeOptions.addArguments("--incognito");
                }
                cd = new CustomDriver(new EdgeDriver(edgeOptions));
            }
            default -> throw new IllegalArgumentException("Invalid Platform Provided: " + browser);
        }
        ThreadLocal<CustomDriver> threadLocalDriver = driverPool.computeIfAbsent(threadId, key -> ThreadLocal.withInitial(() -> cd));
        return threadLocalDriver.get();
    }

    public static CustomDriver getCurrentThreadDriver()
    {
        final long threadId = Thread.currentThread().getId();
        ThreadLocal<CustomDriver> driverThreadLocal =driverPool.get(threadId);
        if(driverThreadLocal != null)
        {
            return driverThreadLocal.get();
        }
        throw new IllegalStateException("No driver associated with the current thread");
    }

    public static void quitCurrentDriver()
    {
        final long threadId = Thread.currentThread().getId();
        ThreadLocal<CustomDriver> threadLocalDriver = driverPool.get(threadId);
        if(Objects.nonNull(threadLocalDriver)){
            CustomDriver driver = threadLocalDriver.get();
            if(Objects.nonNull(driver)){
                driver.clearCookies();
                driver.quit();
                driverPool.remove(threadId);
            }
        }
    }

}
