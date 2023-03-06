package com.qa.shopping.opencart.pages;

import com.qa.shopping.opencart.utils.Constants;
import com.qa.shopping.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AccountPage {

    WebDriver driver;
    private ElementUtil eleUtil;
    private By header = By.cssSelector("div#logo a");
    private By accountSections = By.cssSelector("div#content h2");
    private By searchField = By.name("search");
    private By searchButton = By.cssSelector("div#search button");
    private By logoutLink = By.linkText("Logout");

    public AccountPage(WebDriver driver){
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    public String getAccountPageTitle() {
        return eleUtil.doGetTitle(Constants.ACCOUNTS_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
    }

    public String getAccountPageHeader() {
        return eleUtil.doGetText(header);
    }

    public boolean isLogoutLinkExist() {
        return eleUtil.doIsDisplayed(logoutLink);
    }

    public void logout() {
        if(isLogoutLinkExist()) {
            eleUtil.doClick(logoutLink);
        }
    }

    public List<String> getAccountSecList() {
        List<WebElement> accSecList = eleUtil.waitForElementsToBeVisible(accountSections, Constants.DEFAULT_TIME_OUT);
        List<String> accSecValList = new ArrayList<>();
        for (WebElement e : accSecList    ) {
            accSecValList.add(e.getText());
        }
        return accSecValList;
    }

    public boolean isSearchExist() {
        return eleUtil.doIsDisplayed(searchField);
    }

    public SearchResultsPage doSearch(String productName) {
        System.out.println("searching the product: " + productName);
        eleUtil.doSendKeys(searchField, productName);
        eleUtil.doClick(searchButton);
        return new SearchResultsPage(driver);
    }

}
