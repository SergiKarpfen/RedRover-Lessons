
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;


public class HW {
    private static final String BASE_URL = "https://magento.softwaretestingboard.com/";
    @Test
    void testTrainingMessage() throws InterruptedException {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*", "--headless", "--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get(BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        Thread.sleep(2000);

        WebElement trainingBar = driver.findElement(By.id("ui-id-7"));
        WebElement trainingLink = driver.findElement(By.id("ui-id-28"));

        new Actions(driver).moveToElement(trainingBar).perform();
        wait.until(ExpectedConditions.visibilityOf(trainingLink));

        trainingLink.click();

        WebElement messageInfo = driver
                .findElement(By
                        .xpath("//div[contains(@class, 'message info empty')]/div"));
        Assert.assertEquals(messageInfo.getText(), "We can't find products matching the selection.");

        driver.quit();

    }


}
