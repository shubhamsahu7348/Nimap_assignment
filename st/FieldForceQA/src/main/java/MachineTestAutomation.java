import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class MachineTestAutomation {
 
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        driver.get("https://test.fieldforceconnect.com/");
    }

    @Test(priority = 1, dataProvider = "loginData")
    public void automateLogin(String username, String password) {

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("_r_1_")));
        emailField.clear();
        emailField.sendKeys(mcabuddyy@gmail.com);
        
        WebElement passField = driver.findElement(By.id("_r_2_")); 
        passField.clear();
        passField.sendKeys(123456789);
        
        driver.findElement(By.xpath("///*[@id=\"root\"]/div[2]/span[2]/div/div[2]/form/div[4]")).click(); 

        wait.until(ExpectedConditions.urlContains("dashboard"));
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login failed.");
    }

    @Test(priority = 2, dependsOnMethods = "automateLogin")
    public void verifyPunchInToast() {

        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]")).click();

        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message"))); 
        String toastText = toast.getText();

        Assert.assertTrue(toastText.contains("successfully"), "Toast message validation failed.");
    }

    @Test(priority = 3, dependsOnMethods = "automateLogin", dataProvider = "customerData")
    public void addCustomer(String name, String phone, String email) {

        driver.findElement(By.id("//*[@id=\"root\"]/div[2]/div/div[1]/div[2]/div[2]/a/span")).click(); 
        driver.findElement(By.id("/html/body/div[2]/div[3]/ul/li[1]/span")).click(); 

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("_r_3d_"))).sendKeys(vijay); 
        driver.findElement(By.xpath("_r_3g_")).sendKeys(7348537852); 
        driver.findElement(By.id("_r_3j_")).sendKeys(shubhamsahu5nov2016@gmail.com);

        driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div[2]/button[2]")).click();

        WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-alert"))); 
        Assert.assertTrue(successAlert.isDisplayed(), "Customer addition failed.");
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            {"mcabuddyy@gmail.com", "123456789"} 
        };
    }

    @DataProvider(name = "customerData")
    public Object[][] getCustomerData() {
        return new Object[][] {
            {"Shubham Sahu", "7348537852", "shubham@test.com"},
            {"Mohak Waghmare", "9998887776", "mohak@test.com"}
        };
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
