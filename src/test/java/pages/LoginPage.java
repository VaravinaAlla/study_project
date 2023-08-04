package pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public void locateUserNameAndInsertText(String text) {
        $(By.xpath("//input[@data-name='username-input']")).setValue(text);

    }

    public void locatePasswordAndInsertText(String text) {
        $(By.xpath("//input[@data-name='password-input']")).setValue(text);
    }

    public void clickSignIn() {
        $(By.xpath("//button[@data-name='signIn-button']")).click();
    }
}
