package com.qa.shopping.opencart.tests;

import com.qa.shopping.opencart.pages.ProductInfoPage;
import com.qa.shopping.opencart.pages.SearchResultsPage;
import com.qa.shopping.opencart.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class ProductInfoPageTest extends BaseTest {

    @BeforeClass
    public void productInfoSetup() {
        accountPage = loginPage.doLogin(appConfig.getString(env+".LoginPage.userName"), appConfig.getString(env+".LoginPage.password"));
    }

    @Test
    public void productHeaderTest() {
        searchResultPage = accountPage.doSearch("MacBook");
        productInfoPage = searchResultPage.selectProduct("MacBook Pro");
        Assert.assertEquals(productInfoPage.getProductHeader(), "MacBook Pro");
    }

    @Test
    public void productImagesCountTest() {
        searchResultPage = accountPage.doSearch("iMac");
        searchResultPage.selectProduct("iMac");
        Assert.assertEquals(productInfoPage.getProductImagesCount(), Constants.IMAC_IMAGE_COUNT);
    }

    @Test
    public void productInfoTest() {
        searchResultPage = accountPage.doSearch("MacBook");
        productInfoPage = searchResultPage.selectProduct("MacBook Pro");
        Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
        actProductInfoMap.forEach((k,v) -> System.out.println(k + " : " + v));
        softAssert.assertEquals(actProductInfoMap.get("name"), "MacBook Pro11");
        softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
        softAssert.assertEquals(actProductInfoMap.get("price"), "$2000.00");
        softAssert.assertAll();
    }

}
