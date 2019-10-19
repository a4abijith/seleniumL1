package trendNxt;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC002 
{
	static WebDriver driver;
	static String url = "http://10.207.182.111:84/opencart/";
	static String userName, Password;
	static WebDriverWait wait;
	static String rootdirectory = System.getProperty("user.dir");
	
	@SuppressWarnings("resource")
	public static void main(String args[]) throws Throwable 
	{
		/*Method to read Excel file*/
		FileInputStream excelFilePath = new FileInputStream(new File(rootdirectory + File.separator + "src//test//java//trendNxt//login.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(excelFilePath);
		XSSFSheet sheet = workbook.getSheet("openCart Login");
		userName= sheet.getRow(1).getCell(0).getStringCellValue();
		Password= sheet.getRow(1).getCell(1).getStringCellValue();
		
		/*Method to load firefox browser*/
		System.setProperty("webdriver.chrom.driver", rootdirectory
				+ "//Drivers & Jars//chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'login')]")));
		driver.findElement(By.xpath("//a[contains(text(),'login')]")).click();
		
		/*login*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='email']")));
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys(userName);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(Password);
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		/*Assertion for successful login*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'My Account')]")));
		Assert.assertEquals("My Account",driver.findElement(By.xpath("//h1[contains(text(),'My Account')]")).getText());
		System.out.println("Logged in successfully");

		
		
		/*Searching for products in search box*/
		driver.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys("macbook");
		driver.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys(Keys.ENTER);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[@id='container']/div[@id='content']/div[@class='product-list']/div[1]")));
		
		/*Select "Monitors" under Components in the drop down.*/
		Select sel1 = new Select(driver.findElement(By.xpath("//select[@name='category_id']")));
		sel1.selectByValue("28");
		driver.findElement(By.xpath("//input[@id='sub_category']")).click();
		driver.findElement(By.xpath("//input[@id='button-search']")).click();
		
		
		/*Click on "Phones and PDA's" tab*/
		driver.findElement(By.xpath("//a[contains(text(),'Phones & PDAs')]")).click();
		
		/*sort by price (high to low)*/
		Select sort = new Select(driver.findElement(By.xpath("//div[@class='sort']//select[@onchange='location = this.value;']")));
		sort.selectByVisibleText("Price (High > Low)");
		
		/*add to compare*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[5]/div[4]/div[1]/div[1]/div[3]/a[1]")));
		driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[5]/div[4]/div[1]/div[1]/div[3]/a[1]")).click();
		driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[5]/div[4]/div[2]/div[1]/div[3]/a[1]")).click();
		driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[5]/div[4]/div[3]/div[1]/div[3]/a[1]")).click();
		
		
/*		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='close']")));
		driver.findElement(By.xpath("//img[@class='close']")).click();*/
		
		/*view comparison*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'product comparison')]")));
		driver.findElement(By.xpath("//a[contains(text(),'product comparison')]")).click();
		
		/*clicking on 1st phone*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[4]/table[1]/tbody[1]/tr[1]/td[2]/a[1]")));
		driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[4]/table[1]/tbody[1]/tr[1]/td[2]/a[1]")).click();
		
		/*checking description*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[5]/div[4]/ul[1]/li[5]")));
		String description = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[5]/div[4]/ul[1]/li[5]")).getText();
		System.out.println(description);
		
		
		/*adding to cart*/
		driver.findElement(By.xpath("//input[@id='button-cart']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='success']")));
		
		
		/*clicking on shopping cart*/
		driver.findElement(By.xpath("//a[contains(text(),'Shopping Cart')]")).click();
		
		/*checkout*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='button'][contains(text(),'Checkout')]")));
		driver.findElement(By.xpath("//a[@class='button'][contains(text(),'Checkout')]")).click();
		
		
		/*continue1*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='button-payment-address']")));
		driver.findElement(By.xpath("//input[@id='button-payment-address']")).click();
		
		/*continue2*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='button-shipping-address']")));
		driver.findElement(By.xpath("//input[@id='button-shipping-address']")).click();
		
		/*continue3*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='button-shipping-method']")));
		driver.findElement(By.xpath("//input[@id='button-shipping-method']")).click();
		
		
		/*check & continue4*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='1']")));
		driver.findElement(By.xpath("//input[@value='1']")).click();
		driver.findElement(By.xpath("//input[@id='button-payment-method']")).click();
		
		
		/*confirm order*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='button-confirm']")));
		driver.findElement(By.xpath("//input[@id='button-confirm']")).click();
		
		/*confirmation*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Your Order Has Been Processed!')]")));
		driver.navigate().back();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='content'][contains(text(),'Your shopping cart is empty!')]")));
		
		
		/*My account*/
		driver.findElement(By.xpath("//a[contains(text(),'My Account')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'View your order history')]")));
		driver.findElement(By.xpath("//a[contains(text(),'View your order history')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='button']")));
		driver.findElement(By.xpath("//a[@class='button']")).click();
		
		/*Subscribe / unsubscribe to newsletter*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Subscribe / unsubscribe to newsletter')]")));
		driver.findElement(By.xpath("//a[contains(text(),'Subscribe / unsubscribe to newsletter')]")).click();
		
		 /*Specials from extra*/
		driver.findElement(By.xpath("//a[contains(text(),'Specials')]")).click();
		
		/*grid*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[4]/div[2]/div[1]/a[1]")));
		driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[4]/div[2]/div[1]/a[1]")).click();
		
		
		
		/*Logout*/
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='button']")));
		System.out.println("Logged Out");
		
		driver.quit();
	}
}
