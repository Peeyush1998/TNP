package tpack;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import utils.Utilities;

public class LogInTest extends Base {

	LoginPage loginPage;

	public LogInTest() {
		super();
	}

	public WebDriver driver;

	@BeforeMethod
	public void setup() throws InterruptedException {

		driver = initializeBrowserandApplication(prop.getProperty("browser"));
		HomePage homePage = new HomePage(driver);
		loginPage = homePage.navigateToLoginPage();

	}

	@AfterMethod
	public void tearDown() {

		driver.quit();

	}

	@Test(priority = 1, dataProvider = "validCredentialsSupplier")
	public void verifyLoginWithValidCredentials(String email, String password) {

		AccountPage accountPage = loginPage.login(email, password);
		Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption(),
				"Edit Your Account Information option is not displayed");

	}

	@DataProvider(name = "validCredentialsSupplier")
	public Object[][] supplyTestData() {

		Object[][] data = Utilities.getTestDataFromExcelSheet("Login");
		return data;
	}

	@Test(priority = 2)
	public void verifyLoginWithInvalidCredentials() {

		loginPage.login(Utilities.generateEmailWithTimeStamp(), dataProp.getProperty("invalidPassword"));
		Assert.assertTrue(
				loginPage.retrieveEmailPasswordNotMatchingWarningMessageText()
						.contains(dataProp.getProperty("emailPasswordNoMatchWarning")),
				"Expected Warning message is not displayed");

	}

	@Test(priority = 5)
	public void verifyLoginWithoutProvidingCredentials() {

		loginPage.clickOnLoginButton();
		Assert.assertTrue(
				loginPage.retrieveEmailPasswordNotMatchingWarningMessageText()
						.contains(dataProp.getProperty("emailPasswordNoMatchWarning")),
				"Expected Warning message is not displayed");

	}

}
