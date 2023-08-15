package io.qameta.junit5;

import com.codeborne.selenide.Condition;
import dto.RandomTestData;
import helpers.RemoteStart;
import helpers.SetupFunctions;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import pages.LoginPage;
import pages.OrderPage;


public class WebOrderTest extends RemoteStart {

    @Test
    public void checkCreatingOrderIsSuccessfully(){
        LoginPage loginPage = new LoginPage();
        String username = new SetupFunctions().getUsername();
        String password = new SetupFunctions().getPassword();
        loginPage.locateUserNameAndInsertText(username);
        loginPage.locatePasswordAndInsertText(password);
        loginPage.clickSignIn();
        OrderPage orderPage = new OrderPage();
        RandomTestData randomOrder = new RandomTestData();
        orderPage.locateUserNameAndInsertText(randomOrder.generatedRandomName());
        orderPage.locateUserPhoneAndInsertText(randomOrder.generatedRandomPhone());
        orderPage.locateUserCommentAndInsertText(randomOrder.generatedRandomComment());
        orderPage.clickCreateOrderButton();
        orderPage.checkOrderSuccessfullyCreatedPage();
    }

    @Test
    public void checkNonExistentOrder() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithToken();
        OrderPage orderPage = new OrderPage();
        orderPage.clickSearchOrderButton();
        orderPage.checkSearchOrderPopup();
        orderPage.insertNonExistentOrderNumber();
        orderPage.clickSubmitSearchButton();
        orderPage.checkOrderNonFoundMessage();
    }
}
