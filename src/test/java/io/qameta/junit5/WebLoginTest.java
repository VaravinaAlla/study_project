package io.qameta.junit5;

import dto.RandomTestData;
import helpers.RemoteStart;
import helpers.SetupFunctions;
import org.junit.jupiter.api.*;
import pages.LoginPage;

public class WebLoginTest extends RemoteStart {

    @Test
    public void checkLoginIsSuccessfulWithValidValue() {
        LoginPage loginPage = new LoginPage();
        String username = new SetupFunctions().getUsername();
        String password = new SetupFunctions().getPassword();
        loginPage.locateUserNameAndInsertText(username);
        loginPage.locatePasswordAndInsertText(password);
        loginPage.clickSignIn();
        loginPage.checkOrderPageIsOpened();
    }

    @Test
    public void checkErrorModalWindowsIsAppeared() {
        LoginPage loginPage = new LoginPage();
        RandomTestData randomLoginAndPassword = new RandomTestData();
        loginPage.locateUserNameAndInsertText(randomLoginAndPassword.generatedRandomLogin());
        loginPage.locatePasswordAndInsertText(randomLoginAndPassword.generatedRandomPassword());
        loginPage.clickSignIn();
        loginPage.checkModalWindowsWithError();
    }

    @Test
    public void checkLoginIsSuccessfulAfterClosingErrorWindow() {
        LoginPage loginPage = new LoginPage();
        String username = new SetupFunctions().getUsername();
        String password = new SetupFunctions().getPassword();
        RandomTestData randomLoginAndPassword = new RandomTestData();
        loginPage.locateUserNameAndInsertText(randomLoginAndPassword.generatedRandomLogin());
        loginPage.locatePasswordAndInsertText(randomLoginAndPassword.generatedRandomPassword());
        loginPage.clickSignIn();
        loginPage.clickCloseModalWindowButton();
        loginPage.locateUserNameAndInsertText(username);
        loginPage.locatePasswordAndInsertText(password);
        loginPage.clickSignIn();
        loginPage.checkOrderPageIsOpened();
    }

    @Test
    public void checkErrorModalWindowsIsClosed() {
        LoginPage loginPage = new LoginPage();
        RandomTestData randomLoginAndPassword = new RandomTestData();
        loginPage.locateUserNameAndInsertText(randomLoginAndPassword.generatedRandomLogin());
        loginPage.locatePasswordAndInsertText(randomLoginAndPassword.generatedRandomPassword());
        loginPage.clickSignIn();
        loginPage.clickCloseModalWindowButton();
        loginPage.checkLoginPageIsDisplayed();
    }

    @Test
    public void checkSignInButtonIsDisabledWithEmptyLogin() {
        LoginPage loginPage = new LoginPage();
        RandomTestData randomLoginAndPassword = new RandomTestData();
        loginPage.locateUserNameAndInsertText("");
        loginPage.locatePasswordAndInsertText(randomLoginAndPassword.generatedRandomPassword());
        loginPage.checkSignInButtonIsDisable();
    }

    @Test
    public void checkSignInButtonIsDisabledWithEmptyPassword() {
        LoginPage loginPage = new LoginPage();
        RandomTestData randomLoginAndPassword = new RandomTestData();
        loginPage.locateUserNameAndInsertText(randomLoginAndPassword.generatedRandomLogin());
        loginPage.locatePasswordAndInsertText("");
        loginPage.checkSignInButtonIsDisable();
    }

    @Test
    public void checkErrorMessageIsAppearedWithMinLengthLogin() {
        LoginPage loginPage = new LoginPage();
        RandomTestData randomLoginAndPassword = new RandomTestData();
        loginPage.locateUserNameAndInsertText("l");
        loginPage.locatePasswordAndInsertText(randomLoginAndPassword.generatedRandomPassword());
        loginPage.checkErrorMessageUserName();
        loginPage.checkSignInButtonIsDisable();
    }

    @Test
    public void checkErrorMessageIsAppearedWithMinLengthPassword() {
        LoginPage loginPage = new LoginPage();
        RandomTestData randomLoginAndPassword = new RandomTestData();
        loginPage.locateUserNameAndInsertText(randomLoginAndPassword.generatedRandomLogin());
        loginPage.locatePasswordAndInsertText("pass");
        loginPage.checkErrorMessagePassword();
        loginPage.checkSignInButtonIsDisable();
    }

    @Test
    public void checkErrorModalWindowIsAppearedWithMaxLengthLoginAndPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.locateUserNameAndInsertText("tooMuch1LoginDoesn'tApplyhere!");
        loginPage.locatePasswordAndInsertText("tooMuch1LoginDoesn'tApplyhere!");
        loginPage.clickSignIn();
        loginPage.checkAuthorizationErrorPopup();
    }
}
