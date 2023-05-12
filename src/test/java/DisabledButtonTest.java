
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import runner.BasePage;
import runner.BaseTest;


public class DisabledButtonTest extends BaseTest {

    @Test
    public void changeButtonVisibility() throws InterruptedException {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//a[@class='content-block__link']"))).click();
        WebElement okBtn = getDriver().findElement(By.id("ok-button"));
//        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("asd");
//        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
//        System.out.println(okBtn);
        JavascriptExecutor je = (JavascriptExecutor) getDriver();
        je.executeScript("arguments[0].disabled = false", okBtn);
    }
}
