package common;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CustomDriver {

    private final WebDriver webDriver;
    private Wait wait;

    public CustomDriver(WebDriver driver) {webDriver = driver;}

    public WebDriver nativeDriver() {return webDriver;}

    public Wait waitUntil(){
        if(Objects.isNull(wait)){
            wait = new Wait(this);
        }
        return wait;
    }

    public String getElementId(final WebElement element) {
        return ((RemoteWebElement) element).getId();
    }

    public WebElement findElement(final WebElement element){
        if(isElementPresent(element)){
            return element;
        }
        throw new NoSuchElementException("Element was not found.");
    }

    public WebElement findElement(final By locator){
        return waitUntil().condition(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement findChildElement(final SearchContext container, final By locator){
        return container.findElement(locator);
    }

    public List<WebElement> findElements(final By locator){
        return findChildElements(webDriver, locator);
    }

    public List<WebElement> findChildElements(final SearchContext container, final By locator){
        return container.findElements(locator);
    }

    public String getText(final WebElement element){
        return element.getText();
    }

    public String getValue(final WebElement element){
        return getAttribute(element,"value");
    }

    public String getTextOrValue(final WebElement element){
        return (getText(element).isEmpty())
                ? getValue(element)
                : getText(element);
    }

    public List<String> getTextOrValue(final List<WebElement> elements, final Boolean ignoreEmptyOrNull){
        List<String> result = elements.stream()
                .map(this::getText)
                .toList();

        if(ignoreEmptyOrNull){
            result = getTextOrValue(elements).stream()
                    .filter(Objects::nonNull)
                    .filter(String::isEmpty)
                    .toList();
        }
        return result;
    }

    public List<String> getTextOrValue(final List<WebElement> elements){
        return getTextOrValue(elements, true);
    }

    public boolean isAttributePresent(final WebElement element, final String attributeName)
    {
        try {
            element.getAttribute(attributeName);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public String getAttribute(final WebElement element, final String attributeName){
        if(isAttributePresent(element, attributeName)){
            return element.getAttribute(attributeName);
        }
        return null;
    }

    public void enterText(final WebElement element, final String text) {
        clearText(element);
        element.sendKeys(text);
        getJSDriver().executeScript(
                "document" +
                        ".getElemenById('" + getElementId(element) + ")" +
                        ".value = " + text
                        + "'");
    }

    public void clearText(WebElement element){
        element.clear();
        if(!element.getText().isEmpty()){
            while (!element.getAttribute("value").isEmpty()){
                element.sendKeys(Keys.BACK_SPACE);
            }
        }
    }

    public void click(final WebElement element) {
        waitUntil().isClickable(element).click();
    }

    public boolean isElementPresent(final WebElement element){
        try{
            ((RemoteWebElement) element).getId();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresent(final By locator){
        return webDriver.findElements(locator).isEmpty();
    }

    public WebElement scrollTillVisible(final WebElement element){
        getJSDriver().executeScript("document.arguments[0].scrollIntoView()", element);
        return element;
    }

    public WebElement scrollTillVisible(final By locator){
        return scrollTillVisible(findElement(locator));
    }

    public void hoverMouseOver(final WebElement element){
        Actions action = new Actions(webDriver);
        action.moveToElement(element).build().perform();
    }

    public <X> X getScreenShotAs(OutputType<X> target){
        return ((TakesScreenshot) webDriver).getScreenshotAs(target);
    }

    public void quit(){
        webDriver.quit();
    }

    public void goTo(String url){
        if(!url.startsWith("https://")) {
            url = "htps://" + url;
        }
        webDriver.get(url);
    }

    public void maximizeWindow(){
        webDriver.manage().window().maximize();
    }

    public void refreshPage(){
        webDriver.navigate().refresh();
    }

    public Set<Cookie> getCookies(){
       return webDriver.manage().getCookies();
    }

    public void clearCookies(){
        webDriver.manage().deleteAllCookies();
    }

    public void getBack(){
        webDriver.navigate().back();
    }

    public void goForward(){
        webDriver.navigate().forward();
    }

    public JavascriptExecutor getJSDriver()
    {
        return (JavascriptExecutor) webDriver;
    }

    public void clickJS(final WebElement element) {
        getJSDriver().executeScript("arguments[0].click", element);
    }

    public void clickJS(final By locator) {
        clickJS(findElement(locator));
    }

    public Object executeScript(final String script, final Object... args){
        return ((JavascriptExecutor) webDriver).executeScript(script, args);
    }

    public String getTitle(){
       return nativeDriver().getTitle();
    }

    public String getCurrentTabHandle(){
        return nativeDriver().getWindowHandle();
    }

    public String openNewTab(){
        nativeDriver().switchTo().newWindow(WindowType.TAB);
        waitUntil().allAjaxCallsComplete();
        return getCurrentTabHandle();
    }

    public String openNewTab(final String url){
        nativeDriver().switchTo().newWindow(WindowType.TAB);
        goTo(url);
        waitUntil().allAjaxCallsComplete();
        return getCurrentTabHandle();
    }

    public String switchToTabHandle(final String handleName)
    {
        nativeDriver().switchTo().window(handleName);
        return getCurrentTabHandle();
    }

    public String switchToTabIndex(final int tabIndex)
    {
       List<String> tabList = new ArrayList<>(nativeDriver().getWindowHandles());
       nativeDriver().switchTo().window(tabList.get(tabIndex));
       return getCurrentTabHandle();
    }

    public Boolean switchToTabTitle(final String tabTitle){
        List<String> handleList = new ArrayList<>(nativeDriver().getWindowHandles());
        for (String handle:handleList){
            nativeDriver().switchTo().window(handle);
            if(getTitle().toLowerCase().contains(tabTitle.toLowerCase())){
                break;
            }
        }
        return tabTitle.equalsIgnoreCase(getTitle());
    }

    public boolean isChrome() {
        return webDriver instanceof ChromeDriver;
    }

    public boolean isFirefox() {
        return webDriver instanceof FirefoxDriver;
    }

    public boolean isEdge() {
        return webDriver instanceof EdgeDriver;
    }



}
