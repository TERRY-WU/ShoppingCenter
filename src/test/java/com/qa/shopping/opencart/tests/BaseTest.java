package com.qa.shopping.opencart.tests;

import com.qa.shopping.opencart.config.ConfigProvider;
import com.qa.shopping.opencart.factory.DriverFactory;
import com.qa.shopping.opencart.pages.*;
import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

public class BaseTest {

    DriverFactory factory;
    LoginPage loginPage;
    AccountPage accountPage;
    SearchResultsPage searchResultPage;
    ProductInfoPage productInfoPage;
    RegistrationPage registrationPage;
    WebDriver driver;
    Config appConfig;
    String env;

    /**
     * SoftAssert的特点：
     * 如果一个断言失败，会继续执行这个断言下的其他语句或者断言。
     * 也就是一个用例有多个断言，失败了其中一个，并不影响其他断言的执行。
     * 在该用例的最后一个断言后面要调用assertAll ( )方法结束该条用例的执行。
     */
    SoftAssert softAssert;

    @BeforeTest
    public void setup() {
        factory = new DriverFactory();
        driver = factory.initDriver();
        appConfig = ConfigProvider.config("config", "application.conf");
        if (!"".equals(appConfig.getString("env"))) {
            env = appConfig.getString("env");
        } else {
            env = "dev";
        }
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
        System.out.println("init work is done....!");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
