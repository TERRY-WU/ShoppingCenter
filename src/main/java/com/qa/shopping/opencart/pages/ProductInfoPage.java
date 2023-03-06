package com.qa.shopping.opencart.pages;

import com.qa.shopping.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoPage {

    ElementUtil eleUtil;
    private By productHeader = By.xpath("//div[@id='content']//h1");
    private By productImages = By.cssSelector("ul.thumbnails img");
    private By productMetaData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
    private By productPriceData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");
    private By qty = By.id("input-quantity");
    private By addToCartBtn = By.id("button-cart");
    private Map<String, String> productInfoMap;

    public ProductInfoPage(WebDriver driver){
        eleUtil = new ElementUtil(driver);
    }

    public String getProductHeader() {
        return eleUtil.doGetText(productHeader);
    }

    public int getProductImagesCount() {
        return eleUtil.waitForElementsToBeVisible(productImages, 10).size();
    }

    public Map<String, String> getProductInfo() {
        productInfoMap = new HashMap<>();
        productInfoMap.put("Name", getProductHeader());
        getProductMetaData();
        getProductPriceData();
        return productInfoMap;
    }

    public void getProductMetaData() {
        List<WebElement> metaDataList = eleUtil.getElements(productMetaData);
        for (WebElement e : metaDataList) {
            String text = e.getText();
            String[] meta = text.split(":");
            String metaKey = meta[0].trim();
            String metaValue = meta[1].trim();
            productInfoMap.put(metaKey, metaValue);
        }
    }

    public void getProductPriceData() {
        List<WebElement> priceDataList = eleUtil.getElements(productPriceData);
        String price = priceDataList.get(0).getText().trim();
        String exPrice = priceDataList.get(1).getText().trim();
        productInfoMap.put("Price", price);
        productInfoMap.put("ExTaxPrice", exPrice);
    }

}
