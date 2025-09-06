package DataHelper;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.fail;

public class PurchaseTour {
    public PurchaseTour() {

    }

    private static final ElementsCollection notifications = $$("[class='input__sub']");

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

    private static final SelenideElement closerButton =
            $("[class='icon icon_size_s icon_name_close icon_theme_alfa-on-color']");


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

    public void checkMessageFilledField() {
        $$(".input__sub").find(exactText("Поле обязательно для заполнения")).shouldBe(visible);

    }

    public void checkMessageWrongFormat() {
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    public void checkMessageLastYear() {
        $$(".input__sub").find(exactText("Истёк срок действия карты")).shouldBe(visible);
    }

    ////попытка реализовать проверку отображения двух модалок
    public void checkDouble() {
        boolean isVisible1 = successBuy.isDisplayed();
        boolean isVisible2 = rejected.isDisplayed();
        Duration.ofSeconds(10);
        if (isVisible1 && isVisible2) {
        } else {
            fail();
        }
    }

}



