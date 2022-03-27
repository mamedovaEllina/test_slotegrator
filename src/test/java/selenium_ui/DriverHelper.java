package selenium_ui;

import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverHelper {
    private static final Logger LOGGER = LogManager.getLogger(DriverHelper.class);
    private static RemoteWebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            LOGGER.debug("Запускаю Chrome");
            WebDriverManager.chromedriver().setup();
            ChromeOptions opts = new ChromeOptions();
            opts
                    // .addArguments("--headless", "--disable-gpu")
                    .addArguments("--disable-gpu")
                    .setAcceptInsecureCerts(true);
            driver = new ChromeDriver(opts);
            driver.manage().window().maximize();
        }
        WebDriverRunner.setWebDriver(driver);
        return driver;
    }

    public static void killDriver() {
        if (driver != null) {
            LOGGER.debug("Закрываю браузер");
            driver.close();
            driver.quit();
            driver = null;
        }
    }

}
