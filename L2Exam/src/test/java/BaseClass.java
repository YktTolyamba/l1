import aquality.selenium.browser.AqualityServices;
import org.example.utils.BrowserUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass {

    @BeforeMethod
    public void setUp() {
        BrowserUtils.maximize();
    }

    @AfterMethod
    public void tearDown() {
        AqualityServices.getBrowser().quit();
    }

}
