package com.qa.shopping.opencart.factory;

import com.typesafe.config.Config;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

    private Config appConfig;
    private ChromeOptions chromeOptions;
    private FirefoxOptions firefoxOptions;
    private String env;

    public OptionsManager(Config config) {
        this.appConfig = config;
        if (!"".equals(appConfig.getString("env"))) {
            env = appConfig.getString("env");
        }
    }

    public ChromeOptions getChromeOptions() {
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        if (Boolean.parseBoolean(appConfig.getString("headless"))) {
            chromeOptions.addArguments("--headless");
        }
        if (Boolean.parseBoolean(appConfig.getString("incognito"))) {
            chromeOptions.addArguments("--incognito");
        }
        return chromeOptions;
    }

    public FirefoxOptions getFirefoxOptions() {
        firefoxOptions = new FirefoxOptions();
        if (Boolean.parseBoolean(appConfig.getString("headless"))) firefoxOptions.addArguments("--headless");
        if (Boolean.parseBoolean(appConfig.getString("incognito"))) firefoxOptions.addArguments("--incognito");
        return firefoxOptions;
    }

}
