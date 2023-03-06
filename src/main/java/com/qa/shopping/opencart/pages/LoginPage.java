package com.qa.shopping.opencart.pages;

import com.qa.shopping.opencart.utils.Constants;
import com.qa.shopping.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;
    private ElementUtil eleUtil;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    private By emailId = By.id("input-email");
    private By password = By.id("input-password");
    private By loginBtn = By.xpath("//input[@value='Login']");
    private By registerLink = By.linkText("Register");
    private By forgotPwdLink = By.linkText("Forgotten Password");
    private By loginErrorMesg = By.cssSelector("div.alert.alert-danger.alert-dismissible");

    public String getLoginPageTitle() {
        return eleUtil.doGetTitle(Constants.LOGIN_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
    }

    public boolean getLoginPageUrl() {
        return eleUtil.waitForURLToContain(Constants.LOGIN_PAGE_URL_FRACTION, Constants.DEFAULT_TIME_OUT);
    }

    public boolean isForgotPwdLinkExist() {
        return eleUtil.doIsDisplayed(forgotPwdLink);
    }

    public boolean isRegisterLinkExist() {
        return eleUtil.doIsDisplayed(registerLink);
    }

    public AccountPage doLogin(String un, String pwd) {
        System.out.println("UserName: " + un + " " + "Password: " + pwd);
        eleUtil.doSendKeys(emailId, un);
        eleUtil.doSendKeys(password, pwd);
        eleUtil.doClick(loginBtn);
        return new AccountPage(driver);
    }

    public boolean doLoginWithWrongCredentials(String un, String pwd) {
        System.out.println("try to login with wrong credentials: " + un + " : " + pwd);
        eleUtil.doSendKeys(emailId, un);
        eleUtil.doSendKeys(password, pwd);
        eleUtil.doClick(loginBtn);

        String errorMesg = eleUtil.doGetText(loginErrorMesg);
        System.out.println(errorMesg);
        if(errorMesg.contains(Constants.LOGIN_ERROR_MESSG)) {
            System.out.println("login is not done....");
            return false;
        }
        return true;
    }

    public RegistrationPage goToRegisterPage() {
        eleUtil.doClick(registerLink);
        return new RegistrationPage(driver);
    }

}
