package runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.Properties;

public abstract class BaseTest {

    private WebDriver driver;


    @BeforeMethod
    protected void beforeMethod() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--accept-lang=en;--window-size=1920,1080;--headless");
        driver = new ChromeDriver(chromeOptions);
        driver.get(getWeb());
        loginWeb();

    }
    static Properties properties = new Properties();

    public static Properties getProperties() {
        return properties;
    }

    public String getWeb() {
        return "http://localhost:8080/"; //String.format("http://localhost:%s/", getProperties().getProperty("local.port"));
    }

    public void loginWeb() {
        login(driver);
    }

    public void login (WebDriver driver) {
        driver.findElement(By.name("j_username")).sendKeys("admin");
        driver.findElement(By.name("j_password")).sendKeys("9cc1904b9f5141b9898390c56a42b475");
        driver.findElement(By.name("Submit")).click();
    }

    @AfterMethod
    protected void afterMethod() {
        driver.quit();
    }


    protected WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait2() {
       return new WebDriverWait(getDriver(), Duration.ofSeconds(2));
    }
}
