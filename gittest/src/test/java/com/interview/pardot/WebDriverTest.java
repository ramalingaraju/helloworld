package com.interview.pardot;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.TestCase;

public class WebDriverTest extends TestCase{
	
	WebDriver driver;
	
	@Override
	protected void setUp() {
		driver = new FirefoxDriver();
	}
	
	@Test
	public void testLogin() throws Exception {
		driver.get("https://pi.pardot.com");
		System.out.println();
		WebElement email = driver.findElement(By.id("email_address"));
		email.sendKeys("pardot.applicant@pardot.com");

		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("Applicant2012");

		WebElement login = driver.findElement(By.name("commit"));
		login.submit();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//loadList();
		saveList();
	}
	
	private void loadList() throws InterruptedException {
		
			WebElement marketing = driver.findElement(By.id("mark-tog"));
			assertEquals("Name of the link must be Marketing", "Marketing", marketing.getText());
			
			WebElement marketingDropMenu = driver.findElement(By.xpath("//*[@id='dropmenu-marketing']"));
			WebElement segmentationMenuElement = marketingDropMenu.findElement(By.xpath(".//li/a[contains(text(),'Segmentation')]"));
			WebElement listMenuElement = marketingDropMenu.findElement(By.xpath(".//li/ul/li/a[contains(text(),'Lists')]"));
			
			Actions action = new Actions(driver);
			action.moveToElement(marketing).perform();
			
			waitForElementTillClickable(segmentationMenuElement);
			action.moveToElement(segmentationMenuElement).perform();
			
			waitForElementTillClickable(listMenuElement);
			action.click(listMenuElement).perform();
		
	}
	
	private void saveList() {
		//driver.get("https://pi.pardot.com/prospect/read/id/90799832");
		//WebElement lists = driver.findElement(By.xpath("//*[@class='navbar-inner']/ul/li/a[contains(@href, '/list/prospect/prospect_id/') and contains(text(),'Lists')]"));
		//lists.click();
		
		driver.get("https://pi.pardot.com/list/prospect/prospect_id/90800974");
		try{
			Actions act = new Actions(driver);
			act.moveToElement(driver.findElement(By.xpath("//*[starts-with(@id, 'sel')]/a/span[contains(text(), 'Select a list to add...')]"))).perform();
			act.click().perform();
			//driver.findElement(By.xpath("//*[starts-with(@id, 'sel') ]")).click();
			
			WebElement listInput = driver.findElement(By.cssSelector("div.chzn-search > input[type='text']"));
			listInput.sendKeys("codeTest");
			
			WebElement results = driver.findElement(By.xpath("//*[starts-with(@id, 'sel')]/div[@class='chzn-drop']/ul[@class='chzn-results']/li[contains(text(),'codeTest')]"));
		    results.click();
			System.out.println();
			
		} catch(Exception ex) {
			System.out.println();
		}
	}
	
	
	private void waitForElementTillClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void tearDown() {
		driver.close();
	}
}
