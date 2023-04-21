package tpack;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.Base;
import pages.AccountSuccessPage;
import pages.HomePage;
import pages.RegisterPage;
import utils.Utilities;

public class RegisterTest extends Base {

	RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;
	
	public RegisterTest() {
		super();
	}
	
	public WebDriver driver;
	
	@BeforeMethod
	public void setup() throws InterruptedException {
		
		driver = initializeBrowserandApplication(prop.getProperty("browser"));
		HomePage homePage = new HomePage(driver);
		registerPage = homePage.navigateToRegisterPage();
		
	}
	
	@AfterMethod
	public void tearDown() {
		
		driver.quit();
		
	}
	
	@Test(priority=1)
	public void verifyRegisteringAnAccountWithMandatoryFields() {
		
		accountSuccessPage = registerPage.registerWithMandatoryFields(dataProp.getProperty("firstName"),dataProp.getProperty("lastName"),Utilities.generateEmailWithTimeStamp(),dataProp.getProperty("telephoneNumber"),prop.getProperty("validPassword"));
		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(),dataProp.getProperty("accountSuccessfullyCreatedHeading"),"Account Success page is not displayed");
	
	}
	
	@Test(priority=2)
	public void verifyRegisteringAccountByProvidingAllFields() {
		
		accountSuccessPage = registerPage.registerWithAllFields(dataProp.getProperty("firstName"),dataProp.getProperty("lastName"),Utilities.generateEmailWithTimeStamp(),dataProp.getProperty("telephoneNumber"),prop.getProperty("validPassword"));
		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(),dataProp.getProperty("accountSuccessfullyCreatedHeading"),"Account Success page is not displayed");
	
	}}
