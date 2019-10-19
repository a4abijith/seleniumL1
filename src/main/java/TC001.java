


import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TC001 
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
		System.setProperty("webdriver.gecko.driver", rootdirectory
				+ "//Drivers & Jars//geckodriver.exe");
		driver = new FirefoxDriver();
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
//		Assert.assertEquals("My Account",driver.findElement(By.xpath("//h1[contains(text(),'My Account')]")).getText());
		System.out.println("Logged in successfully");
		Thread.sleep(3000);
		
		/*Logout*/
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='button']")));
		System.out.println("Logged Out");
		driver.quit();
	}

}
