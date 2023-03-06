package com.qa.shopping.opencart.tests;

import com.qa.shopping.opencart.pages.ProductInfoPage;
import com.qa.shopping.opencart.pages.SearchResultsPage;
import com.qa.shopping.opencart.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class AccountPageTest extends BaseTest {

    @BeforeClass
    public void accPageSetup() {
        System.out.println(appConfig.getString(env + ".LoginPage.userName"));
        accountPage = loginPage.doLogin(appConfig.getString(env + ".LoginPage.userName"), appConfig.getString(env + ".LoginPage.password"));
    }

    @Test
    public void accPageTitleTest() {
        String accountPageTitle = accountPage.getAccountPageTitle();
        System.out.println(accountPageTitle);
        Assert.assertEquals(accountPageTitle, Constants.ACCOUNTS_PAGE_TITLE);
    }

    @Test
    public void accPageHeaderTest() {
        String header = accountPage.getAccountPageHeader();
        Assert.assertEquals(header, Constants.ACCOUNTS_PAGE_HEADER);
    }

    @Test
    public void isLogoutExistTest() {
        Assert.assertTrue(accountPage.isLogoutLinkExist());
    }

    @Test
    public void accPageSectionsTest() {
        List<String> accountSecList = accountPage.getAccountSecList();
        Assert.assertEquals(accountSecList, Constants.getExpectedAccSecList());
    }

    @DataProvider
    public Object[][] productData() {
        return new Object[][]{
                {"MacBook"},
                {"Apple"},
                {"Samsung"}
        };
    }

    @Test(dataProvider = "productData")
    public void searchTest(String productName) {
        searchResultPage = accountPage.doSearch(productName);
        Assert.assertTrue(searchResultPage.getProductListCount() > 0);
    }

    @DataProvider
    public Object[][] productSelectData() {
        return new Object[][]{
                {"MacBook", "MacBook Pro"},
                {"iMac", "iMac"},
                {"Samsung", "Samsung SyncMaster 941BW"},
                {"apple", "Apple Cinema 30\""},
        };
    }

    @Test(dataProvider = "productSelectData")
    public void selectProductTest(String productName, String mainProductName) {
        searchResultPage = accountPage.doSearch(productName);
        productInfoPage = searchResultPage.selectProduct(mainProductName);
        Assert.assertEquals(productInfoPage.getProductHeader(), mainProductName);
    }

}
