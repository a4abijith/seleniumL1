package trendNxt;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC006 
{
	static WebDriver driver;
	static String url = "http://10.207.182.111:84/opencart/";
	static String userName, Password;
	static WebDriverWait wait;
	static String rootdirectory = System.getProperty("user.dir");

	@SuppressWarnings("resource")
	@BeforeClass
	public static void beforeClass() throws Throwable 
	{
		System.out.println("Before class");
		/*Method to read Excel file*/
		FileInputStream excelFilePath = new FileInputStream(new File(rootdirectory + File.separator + "src//test//java//trendNxt//login.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(excelFilePath);
		XSSFSheet sheet = workbook.getSheet("openCart Login");
		userName= sheet.getRow(1).getCell(0).getStringCellValue();
		Password= sheet.getRow(1).getCell(1).getStringCellValue();
		
		/*Method to load firefox browser*/
		System.setProperty("webdriver.chrome.driver", rootdirectory
				+ "//Drivers & Jars//chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Before
	public void before() 
	{
		System.out.println("Before");
		
		/*Hitting URL*/
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

	}

	@Test
	public void test() 
	{
		System.out.println("Test");
		

		/*Searching for products in search box*/
		driver.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys("canon");
		driver.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys(Keys.ENTER);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Canon EOS 5D')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Canon EOS 5D')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='cart']//div//input[@value='1']")));
		
		/*Selecting the color*/
		Select sort = new Select(driver.findElement(By.xpath("//select[@name='option[226]']")));
		sort.selectByValue("15");
		
		
		/*changing the quantity & adding to cart*/
		driver.findElement(By.xpath("//div[@class='cart']//div//input[@value='1']")).clear();
		driver.findElement(By.xpath("//div[@class='cart']//div//input[@value='1']")).sendKeys("2");
		driver.findElement(By.xpath("//input[@id='button-cart']")).click();
		
		/*success mesage*/
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='success']")));
	
		/*clicking on shopping cart*/
		driver.findElement(By.xpath("//a[contains(text(),'Shopping Cart')]")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='cart-total']//tbody//tr[2]//td[2]")));
		String total = driver.findElement(By.xpath("//div[@class='cart-total']//tbody//tr[2]//td[2]")).getText();
		total = total.replace("$", "");
		System.out.println(total);
		float amount =  Float.parseFloat(total); 
		if(amount<200)
		{
			/*continue shopping*/
			driver.findElement(By.xpath("//a[contains(text(),'Continue Shopping')]")).click();
		}
		else
		{
			System.out.println("Amount greater than 200. So Logout");
		}
		
	}

	@After
	public void after() 
	{
		System.out.println("After");
		/*Logout*/
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='button']")));
		System.out.println("Logged Out");
	}

	@AfterClass
	public static void AfterClass() 
	{
		driver.quit();
		System.out.println("After class - Driver instance closed");
	}
}
