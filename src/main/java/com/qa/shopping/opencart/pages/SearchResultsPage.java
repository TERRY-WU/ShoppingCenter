package com.qa.shopping.opencart.pages;

import com.qa.shopping.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class SearchResultsPage {

    WebDriver driver;
    ElementUtil eleUtil;

    private By productResults = By.cssSelector("div.caption a");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    public int getProductListCount() {
        return eleUtil.waitForElementsToBeVisible(productResults, 10, 2000).size();
    }

    public ProductInfoPage selectProduct(String mainProductName) {
        List<WebElement> searchList = eleUtil.waitForElementsToBeVisible(productResults, 10, 2000);
        for (WebElement e : searchList) {
            String text = e.getText();
            if(text.equals(mainProductName)) {
                e.click();
                break;
            }
        }
        return new ProductInfoPage(driver);
    }

}
