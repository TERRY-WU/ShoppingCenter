//package com.qa.shopping.opencart.config;
//
//import com.qa.shopping.opencart.factory.OptionsManager;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.safari.SafariDriver;
//import org.springframework.context.annotation.*;
//
//@Configuration
//@ComponentScan({"com.qa.shopping.opencart"})
//@Import({})
//public class MainConfig {
//
////    @Scope("prototype")
//    @Bean
//    @Lazy
//    public ChromeDriver getChromeDriver() {
//        return new ChromeDriver(new OptionsManager(ConfigProvider.config("config",
//                "application.conf")).getChromeOptions());
//    }
//
//    @Bean
//    @Lazy
//    public FirefoxDriver getFireFoxDriver() {
//        return new FirefoxDriver(new OptionsManager(ConfigProvider.config("config",
//                "application.conf")).getFirefoxOptions());
//    }
//
//    @Bean
//    @Lazy
//    public SafariDriver getSafariDriver() {
//        return new SafariDriver();
//    }
//
//}