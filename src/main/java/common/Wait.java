package common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Wait {
    private final CustomDriver driver;
    private final WebDriverWait wait;

    public Wait(CustomDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver.nativeDriver(), Duration.ofSeconds(30));
    }

    public <T> T condition(final Duration timeout,
                           final ExpectedCondition<T> condition,
                           final String errorMessage){
        try {
            return wait
                    .withTimeout(timeout)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(condition);
        }catch (Exception e){
            throw new TimeoutException(errorMessage, e);
        }
    }

    public <T> T condition(final ExpectedCondition<T> condition) {
        return condition(
                Duration.ofSeconds(30),
                condition,
                "Condition not met: " + condition.toString());
    }

    public WebElement isPresent(final WebElement element, final Duration timeout) {
        return condition(
                timeout,
                d -> {
                    if (driver.isElementPresent(element)) {
                        return element;
                    }
                    return null;
                },
                "Element is not present");
    }

    public WebElement isPresent(final WebElement element){
        return isPresent(element, Duration.ofSeconds(30));
    }

    public WebElement isPresent(final By locator){
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement isPresent(final By locator, final Duration timeout) {
        return condition(
                timeout,
                ExpectedConditions.presenceOfElementLocated(locator),
                String.format("Could not find element using provided locator strategy (%s).",locator.toString()));
    }

    public Boolean anyIsPresent(final List<WebElement> elements) {
        return elements
                .stream()
                .anyMatch(driver::isElementPresent);
    }

    public Boolean anyIsPresent(final By locator) {
        return driver.findElements(locator)
                .stream()
                .anyMatch(driver::isElementPresent);
    }

    public boolean allArePresent(final List<WebElement> elements) {
        return condition(
                ExpectedConditions.visibilityOfAllElements(elements))
                .equals(elements);
    }

    public boolean allArePresent(final By locator) {
        return condition(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(locator))
                .equals(driver.findElements(locator));
    }

    public Boolean DOMisLoaded(){
        return driver.executeScript("return document.readyState").toString()
                .equalsIgnoreCase("complete");
    }

    public void allAjaxCallsComplete(){
        condition(
                Duration.ofSeconds(20),
                d->{
                    if(DOMisLoaded()) {
                        return Boolean.parseBoolean(driver.getJSDriver().executeScript(
                                "if (typeof jQuery !== 'undefined') " +
                                        "{ return jQuery.active == 0;} " +
                                        "return true;").toString());
                    }
                    return false;
                },
                "Timed out waiting for either DOM to load, or all AJAX calls to finish.");
    }

    public WebElement isClickable(final WebElement element){
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement isClickable(final By locator){
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement isVisible(final WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement isVisible(final By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public Boolean isNotVisible(final WebElement element){
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public Boolean isNotVisible(final By locator){
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

}
