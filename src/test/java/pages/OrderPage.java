package pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class OrderPage {

    public void checkStatusOrderPageIsOpened(){
        $(By.xpath("//input[@data-name='useless-input']")).shouldBe(Condition.exist, Condition.visible);
    }
    @Step ("Insert user name")
    public void locateUserNameAndInsertText(String text) {
        $(By.xpath("//input[@data-name='username-input']")).setValue(text);
    }

    @Step ("Insert phone number")
    public void locateUserPhoneAndInsertText(String text) {
        $(By.xpath("//input[@data-name='phone-input']")).setValue(text);
    }

    @Step ("Insert Comment")
    public void locateUserCommentAndInsertText(String text) {
        $(By.xpath("//input[@data-name='comment-input']")).setValue(text);
    }

    @Step ("Click Create Order button")
    public void clickCreateOrderButton() {
        $(By.xpath("//button[@data-name='createOrder-button']")).click();
    }

    @Step ("Click Search order button")
    public void clickSearchOrderButton() {
        $(By.xpath("//button[@data-name='openStatusPopup-button']")).click();
    }

    public void insertOrderNumber(String number) {
        $(By.xpath("//input[@data-name='searchOrder-input']")).setValue(number);
    }
    public void insertNonExistentOrderNumber() {
        $(By.xpath("//input[@data-name='searchOrder-input']")).setValue("0000");
    }

    @Step ("Click Submit Search button")
    public void clickSubmitSearchButton() {
        $(By.xpath("//button[@data-name = 'searchOrder-submitButton']")).click();
    }

    public void checkOrderNonFoundMessage() {
        $(By.xpath("//*[@data-name = 'orderNotFound-container']")).shouldBe(Condition.exist, Condition.visible);
    }

    public void checkSearchOrderPopup() {
        $(By.xpath("//div[@data-name = 'searchOrder-popup']"))
                .shouldBe(Condition.exist, Condition.visible);
    }

    public void checkOrderSuccessfullyCreatedPage() {
        $(By.xpath("//div[@data-name = 'orderSuccessfullyCreated-popup']"))
                .shouldBe(Condition.exist, Condition.visible);
        $(By.xpath("//button[@data-name = 'orderSuccessfullyCreated-popup-ok-button']"))
                .shouldBe(Condition.exist, Condition.visible);
        $(By.xpath("//button[@data-name = 'orderSuccessfullyCreated-popup-close-button']"))
                .shouldBe(Condition.exist, Condition.visible);
    }

}
