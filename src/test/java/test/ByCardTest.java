package test;


import DataHelper.DataGenerator;
import DataHelper.PurchaseTour;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import dataSQL.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static DataHelper.PurchaseTour.enteringForm;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ByCardTest {
    private final DataGenerator.CardInfo invalidData = DataGenerator.getInvalidDataCard();
    private final DataGenerator.CardInfo validDataWithCardApproved = DataGenerator.getValidDataCard
            (DataGenerator.getValidCardNumberApproved());
    private final DataGenerator.CardInfo validDataWithCardDeclined = DataGenerator.getValidDataCard
            (DataGenerator.getValidCardNumberDeclined());
    private final PurchaseTour purchaseTour = new PurchaseTour();

    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        open("http://localhost:8080");
    }

    @BeforeAll
    static void setupAllureReports() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true));
    }

    @AfterEach
    void cleanDBCard() {
        SQLHelper.cleanDatabase();
    }


    @Test
    @DisplayName("Проверка пустой строки номера карты")
    void checkEmptyCardString() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(
                "",
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка некорректного номера карты (1 цифра)")
    void checkWrongCardNumber() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(
                invalidData.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Проверка некорректного номера карты (спецсимволы)")
    void checkWrongCardNumber_1() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm("*#$%^&&&^^%$$$##",
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка некорректного номера карты (15 цифр)")
    void checkWrongCardNumber_2() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm("123456789012345",
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Проверка некорректного номера карты (латиница)")
    void checkWrongCardNumber_3() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm("qwertyuiopasdfgh",
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка некорректного номера карты (кириллица)")
    void checkWrongCardNumber_4() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm("йцукенгшщздлорпа",
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка некорректного номера карты (все нули)")
    void checkWrongCardNumber_5() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm("0000000000000000",
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Проверка пустой строки поля месяц")
    void checkEmptyMonthString() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                "",
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка некорректного ввода месяца (1 цифра)")
    void checkWrongMonth() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                invalidData.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Проверка некорректного ввода месяца (13-й месяц)")
    void checkWrongMonth_1() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                "13",
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Проверка некорректного ввода месяца (все нули)")
    void checkWrongMonth_2() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                "00",
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();

    }

    @Test
    @DisplayName("Проверка некорректного ввода месяца (спецсимволы)")
    void checkWrongMonth_3() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                "&^",
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка некорректного ввода месяца (латиница)")
    void checkWrongMonth_4() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                "gf",
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка некорректного ввода месяца (кириллица)")
    void checkWrongMonth_5() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                "ар",
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка пустой строки поля год")
    void checkEmptyYearString() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                "",
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка проверка некорректного года (одна цифра)")
    void checkWrongYear() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                "3",
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Проверка проверка некорректного года (прошлый год)")
    void checkLastYear() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                invalidData.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageLastYear();
    }

    @Test
    @DisplayName("Проверка проверка некорректного года (спецсимволы)")
    void checkWrongYear_1() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                "%^",
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка проверка некорректного года (латиница)")
    void checkWrongYear_2() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                "fg",
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка проверка некорректного года (кириллица)")
    void checkWrongYear_3() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                "по",
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка пустой строки поля владелец")
    void checkEmptyCardHolderString() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                "",
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка некорректного владельца (цифры)")
    void checkWrongCardHolder() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                "234244",
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Проверка некорректного владельца (спецсимволы)")
    void checkWrongCardHolder_1() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                "№%:,.;",
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Проверка некорректного владельца (кириллица)")
    void checkWrongCardHolder_2() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                "Сергей Петров",
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Проверка пустого поля CVC")
    void checkEmptyCVcString() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                "");
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();

    }

    @Test
    @DisplayName("Проверка некорректного заполнения CVC (три нуля)")
    void checkWrongCVc() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                "000");
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();

    }

    @Test
    @DisplayName("Проверка некорректного заполнения CVC (две цифры)")
    void checkWrongCVc_1() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                invalidData.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Проверка некорректного заполнения CVC (спецсимволы)")
    void checkWrongCVc_2() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                "^&*");
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка некорректного заполнения CVC (латиница)")
    void checkWrongCVc_3() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                "gfh");
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();
    }

    @Test
    @DisplayName("Проверка некорректного заполнения CVC (кириллица)")
    void checkWrongCVc_4() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                "про");
        purchaseTour.clickContinueButton();
        purchaseTour.checkMessageFilledField();

    }

    @Test
    @DisplayName("Проверка задвоения заголовка (скрытый элемент интерфейса) о результате операции: если тест прошел то проблема")
    void checkDoubleNotification() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(
                "1234512345123451",
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.successBuy();
        purchaseTour.rejected();
        purchaseTour.checkDouble();
        ///тест не работает так как надо
    }

    @Test
    @DisplayName("Проверка покупки с валидной картой и проверка записи в БД")
    void checkHappyPathByCard_1() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(
                validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.successBuy();
        SQLHelper.getStatusCard();
        assertEquals("APPROVED", SQLHelper.getStatusCard());

    }

    @Test
    @DisplayName("Проверка покупки с невалидной картой и проверка записи в БД")
    void checkHappyPathByCard_2() {
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        enteringForm(
                validDataWithCardDeclined.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.rejected();
        assertEquals("DECLINED", SQLHelper.getStatusCard());

    }

}
