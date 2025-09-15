package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class PurchaseTour {


    private static final SelenideElement successBuy = $$("[class='notification__content']")
            .findBy(exactText("Операция одобрена Банком."));

    private static final SelenideElement rejected = $$("[class='notification__content']")
            .findBy(exactText("Ошибка! Банк отказал в проведении операции."));

    private static final ElementsCollection cardHolder = $$("[class='input__control']");

    private static final SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");

    private static final SelenideElement month = $$(".input__control")
            .findBy(Condition.attribute("placeholder", "08"));

    private static final SelenideElement year = $$(".input__control")
            .findBy(Condition.attribute("placeholder", "22"));

    private static final SelenideElement cvc = $$(".input__control")
            .findBy(Condition.attribute("placeholder", "999"));

    private static final SelenideElement orderButton = $$("[class='button__content']")
            .findBy(Condition.text("Продолжить"));


    public static void enteringForm(String cardNumb, String months, String years, String cardHolders, String cvC) {
        cardNumber.setValue(cardNumb);
        month.setValue(months);
        year.setValue(years);
        cardHolder.get(3).setValue(cardHolders);
        cvc.setValue(cvC);
    }

    public void clickContinueButton() {
        orderButton.click();
    }

    public void successBuy() {
        successBuy.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void rejected() {
        rejected.shouldBe(visible, Duration.ofSeconds(10));
    }


    public void findErrorMessage(String expectedText) {
        $$(".input__sub").find(exactText(expectedText)).shouldBe(visible);
    }

    public void clickCard() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
    }

    public void clickCredit() {
        $$(".button__content").find(exactText("Купить в кредит")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты")).shouldBe(visible);
    }


}



