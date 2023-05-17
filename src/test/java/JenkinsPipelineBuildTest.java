import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class JenkinsPipelineBuildTest extends BaseTest {

    public void createPipeline() {
        WebElement newJob = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//*[@href='/view/all/newJob']")));
        newJob.click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[text()='Pipeline']"))).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(getProjectName());
        getDriver().findElement(By.id("ok-button")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//button[@name='Submit']"))).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("//a[text()='Dashboard']"))).click();
    }



    @Test
    public void createPipe() {
        createPipeline();


    }

    @Test(dependsOnMethods = "createPipe")
    public void testRunBuildFromDashboard () {
        getDriver().findElement(By.xpath("//a[@tooltip='Schedule a Build for "+getProjectName()+"']")).click();

        getDriver().findElement(By.linkText("Build History")).click();

        Assert.assertTrue(getDriver().findElements(By
                .xpath("//*[@class='jenkins-table__link model-link']"))
                .stream().map(WebElement::getText).toList()
                .contains(getProjectName()));
    }

    @Test(dependsOnMethods = "testRunBuildFromDashboard")
    public void deleteWholePipeline() {
        getDriver().findElement(By.xpath("//*[@href='job/"+getProjectName()+"/']")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[contains(text(),'Delete Pipeline')]"))).click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        Assert.assertFalse(getDriver().findElements(By
                        .xpath("//a[@class='jenkins-table__link model-link inside']"))
                        .stream().map(WebElement::getText).toList().contains(getProjectName()));
    }

    @Test(dependsOnMethods = "createPipe")
    public void buildNowFromPipelineView() {
        getDriver().findElement(By.xpath("//*[@href='job/"+getProjectName()+"/']")).click();

        if (!getDriver().findElement(By.xpath("//div[@id='no-builds']")).isDisplayed()) {
            getDriver().findElement(By.xpath("//a[@href='/toggleCollapse?paneId=buildHistory']")).click();
        }

        getDriver().findElement(By
                .xpath("//a[@href='/job/"+getProjectName()+"/build?delay=0sec']")).click();

        int numberOfStartedBuilds = 1;
        boolean lastBuildIsPresent = getWait5().until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("//span[@class='build-status-icon__outer']//*[name()='svg']["+numberOfStartedBuilds+"]")))
                .isDisplayed();
        List<WebElement> list = new ArrayList<>();
        if (lastBuildIsPresent) {
            list = getDriver().findElements(By
                    .xpath("//span[@class='build-status-icon__outer']//*[name()='svg']"));
        }

        try {
            Assert.assertEquals(Color.fromString(list.get(0).getCssValue("color"))
                    .asHex(), "#1ea64b");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            System.out.println("there are no builds in the 'Build History' list");
            e.printStackTrace();
        }
    }
}


