package com.qa.shopping.opencart.tests;

import com.qa.shopping.opencart.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test(priority = 1)
    public void loginPageTitleTest() {
        String actTitle = loginPage.getLoginPageTitle();
        System.out.println(actTitle);
        Assert.assertEquals(actTitle, appConfig.getString(env+".LoginPage.title"));
    }

    @Test(priority = 2)
    public void loginPageUrlTest() {
        Assert.assertTrue(loginPage.getLoginPageUrl());
    }

    @Test(priority = 3)
    public void forgotPwdLinkTest() {
        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
    }

    @Test(priority = 4)
    public void registerLinkTest() {
        Assert.assertTrue(loginPage.isRegisterLinkExist());
    }

    @Test(priority = 5)
    public void loginTest() {
        accountPage = loginPage.doLogin(appConfig.getString(env+".LoginPage.userName"), appConfig.getString(env+".LoginPage.password"));
        softAssert.assertEquals(accountPage.getAccountPageTitle(), Constants.ACCOUNTS_PAGE_TITLE);
    }

}
