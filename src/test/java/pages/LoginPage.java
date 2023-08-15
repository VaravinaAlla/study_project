package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import helpers.SetupFunctions;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    @Step("Insert User Name")
    public void locateUserNameAndInsertText(String text) {
        $(By.xpath("//input[@data-name='username-input']")).setValue(text);

    }
    @Step ("Insert Password")
    public void locatePasswordAndInsertText(String text) {
        $(By.xpath("//input[@data-name='password-input']")).setValue(text);
    }

    @Step ("Click Sign In button")
    public void clickSignIn() {
        $(By.xpath("//button[@data-name='signIn-button']")).click();
    }

    @Step ("Close modal window")
    public void clickCloseModalWindowButton() {
        $(By.xpath("//button[@data-name='authorizationError-popup-close-button']")).click();
    }

    public OrderPage clickSignInAndGoToOrder() {
        $(By.xpath("//button[@data-name='signIn-button']")).click();
        return new OrderPage();
    }

    public void checkModalWindowsWithError() {
        $(By.xpath("//div[@data-name='authorizationError-popup']")).shouldBe(Condition.exist, Condition.visible);
    }

    public void checkOrderPageIsOpened() {
        $(By.xpath("//button[@data-name='createOrder-button']")).shouldBe(Condition.exist, Condition.visible);
        $(By.xpath("//button[@data-name='openStatusPopup-button']")).shouldBe(Condition.exist, Condition.visible);
    }

    public void checkLoginPageIsDisplayed() {
        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.exist, Condition.visible);
    }

    public void checkSignInButtonIsDisable() {
        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.disabled);
    }

    public void checkErrorMessageUserName() {
        $(By.xpath("//*[@data-name = 'username-input']/..//*[@data-name = 'username-input-error']"))
                .shouldBe(Condition.exist, Condition.visible);
    }

    public void checkErrorMessagePassword() {
        $(By.xpath("//*[@data-name = 'password-input']/..//*[@data-name = 'username-input-error']"))
                .shouldBe(Condition.exist, Condition.visible);
    }

    public void checkAuthorizationErrorPopup() {
        $(By.xpath("//div[@data-name='authorizationError-popup']")).shouldBe(Condition.exist, Condition.visible);
    }

    public void loginWithToken() {
        SetupFunctions setupFunctions = new SetupFunctions();
        Selenide.localStorage().setItem("jwt", setupFunctions.getToken());
        Selenide.refresh();
    }
}
