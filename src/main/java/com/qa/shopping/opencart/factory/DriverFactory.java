package com.qa.shopping.opencart.factory;

import com.qa.shopping.opencart.config.ConfigProvider;
import com.typesafe.config.Config;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DriverFactory {

    public WebDriver driver;
    public static String highlight;
    public OptionsManager optionsManager;
    public Config appConfig;
    String env;
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverFactory.class);
    private static final String OUTPUT_FOLDER = System.getProperty("user.dir")+"/report/screenshots/";

    /**
     * Description: this method is used to initialize the webdriver
     *
     * @return: this will return the driver
     */
    public WebDriver initDriver() {
        LOGGER.info("Initializing driver...! ");
        appConfig = ConfigProvider.config("config", "application.conf");
        if (!"".equals(appConfig.getString("env"))) {
            env = appConfig.getString("env");
        }
        if (env == null) {
            env = "prod";
        }
        String browserName = appConfig.getString("browserName");
        System.out.println("Browser name is: " + browserName);
        highlight = appConfig.getString("highlight");
        optionsManager = new OptionsManager(appConfig);
        if (Boolean.parseBoolean(highlight)) {
            System.out.println("highlight is true...!");
        }
        if (browserName.equalsIgnoreCase("chrome")) {
            tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
//            driver = new ChromeDriver(optionsManager.getChromeOptions());
        } else if (browserName.equalsIgnoreCase("firefox")) {
            tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
//            driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
        } else if (browserName.equalsIgnoreCase("safari")) {
            tlDriver.set(new SafariDriver());
//            driver = new SafariDriver();
        } else {
            System.out.println("Please pass the right browser...!");
        }
//        driver.manage().window().fullscreen();
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        getDriver().get(appConfig.getString(env + ".LoginPage.URL"));
        return getDriver();
    }

    /**
     * get driver
     */
    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

   /* public Config initConfig() {
        return ConfigProvider.config();
    }*/

    /**
     * take screenshot
     */
    public String getScreenshot(String className, String methodName) {
        Path scPath = Paths.get(OUTPUT_FOLDER);
        if (!Files.exists(scPath)) {
            try {
                Files.createDirectories(scPath);
                System.out.println("Screenshots folder created success!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String dateStr = sdFormatter.format(new Date());
        File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = OUTPUT_FOLDER + className + "_" + methodName + "_" + dateStr + ".png";
        System.out.println("path: " + path);
        File destination = new File(path);
        try {
            FileUtils.copyFile(src, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

}
