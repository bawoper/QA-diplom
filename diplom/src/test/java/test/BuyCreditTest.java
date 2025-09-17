package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import dataHelper.DataGenerator;
import dataSQL.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PurchaseTour;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static page.PurchaseTour.enteringForm;

public class BuyCreditTest {
    private final DataGenerator.CardInfo invalidData = DataGenerator.getInvalidDataCard();
    private final DataGenerator.CardInfo validDataWithCardApproved = DataGenerator.getValidDataCard
            (DataGenerator.getValidCardNumberApproved());
    private final DataGenerator.CardInfo validDataWithCardDeclined = DataGenerator.getValidDataCard
            (DataGenerator.getValidCardNumberDeclined());
    private final DataGenerator.FakeCardInfo fakeData = DataGenerator.getFakeData();
    private PurchaseTour purchaseTour;
    private SQLHelper sqlHelper;


    @BeforeEach
    void setUp() {
        open("http://localhost:8080");

    }

    @BeforeEach
    void setUpPage() {
        purchaseTour = new PurchaseTour();
        sqlHelper = new SQLHelper();
    }


    @BeforeAll
    static void setupAllureReports() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true));

    }

    @AfterEach
    void cleanDBCard() {
        sqlHelper.cleanDatabase();
    }

    @Test
    @DisplayName("В кредит: Проверка пустой строки номера карты")
    void checkEmptyCardStringCredit() {
        purchaseTour.clickCredit();
        enteringForm(fakeData.fakeEmpty,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного номера карты (1 цифра)")
    void checkWrongCardNumberCredit() {
        purchaseTour.clickCredit();
        enteringForm(
                invalidData.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного номера карты (спецсимволы)")
    void checkWrongCardNumber_1_Credit() {
        purchaseTour.clickCredit();
        enteringForm(fakeData.fakeSymb,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного номера карты (15 цифр)")
    void checkWrongCardNumber_2_Credit() {
        purchaseTour.clickCredit();
        enteringForm(fakeData.fakeNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного номера карты (латиница)")
    void checkWrongCardNumber_3_Credit() {
        purchaseTour.clickCredit();
        enteringForm(fakeData.fakeWordEn,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного номера карты (кириллица)")
    void checkWrongCardNumber_4_Credit() {
        purchaseTour.clickCredit();
        enteringForm(fakeData.fakeWordRU,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного номера карты (все нули)")
    void checkWrongCardNumber_5_Credit() {
        purchaseTour.clickCredit();
        enteringForm(fakeData.fakeAllZero,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");
    }

    @Test
    @DisplayName("В кредит: Проверка пустой строки поля месяц")
    void checkEmptyMonthStringCredit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                fakeData.fakeEmpty,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного ввода месяца (1 цифра)")
    void checkWrongMonthCredit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                invalidData.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного ввода месяца (13-й месяц)")
    void checkWrongMonth_1_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                fakeData.fakeMonth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного ввода месяца (все нули)")
    void checkWrongMonth_2_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                fakeData.fakeTwoZero,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");

    }

    @Test
    @DisplayName("В кредит: Проверка некорректного ввода месяца (спецсимволы)")
    void checkWrongMonth_3_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                fakeData.fakeSymb,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного ввода месяца (латиница)")
    void checkWrongMonth_4_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                fakeData.fakeWordEn,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного ввода месяца (кириллица)")
    void checkWrongMonth_5_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                fakeData.fakeWordRU,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка пустой строки поля год")
    void checkEmptyYearStringCredit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                fakeData.fakeEmpty,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка проверка некорректного года (одна цифра)")
    void checkWrongYearCredit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                invalidData.moth,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");
    }

    @Test
    @DisplayName("В кредит: Проверка проверка некорректного года (прошлый год)")
    void checkLastYearCredit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                invalidData.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Истёк срок действия карты");
    }

    @Test
    @DisplayName("В кредит: Проверка проверка некорректного года (спецсимволы)")
    void checkWrongYear_1_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                fakeData.fakeSymb,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка проверка некорректного года (латиница)")
    void checkWrongYear_2_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                fakeData.fakeWordEn,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка проверка некорректного года (кириллица)")
    void checkWrongYear_3_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                fakeData.fakeWordRU,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка пустой строки поля владелец")
    void checkEmptyCardHolderStringCredit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                fakeData.fakeEmpty,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного владельца (цифры)")
    void checkWrongCardHolderCredit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                fakeData.fakeNumber,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного владельца (спецсимволы)")
    void checkWrongCardHolder_1_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                fakeData.fakeSymb,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");
    }

    @Test
    @DisplayName("В кредит: Проверка некорректного владельца (кириллица)")
    void checkWrongCardHolder_2_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                fakeData.fakeWordRU,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");
    }

    @Test
    @DisplayName("В кредит: Проверка пустого поля CVC")
    void checkEmptyCVcStringCredit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                fakeData.fakeEmpty);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");

    }

    @Test
    @DisplayName("В кредит: Проверка некорректного заполнения CVC (три нуля)")
    void checkWrongCVcCredit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                fakeData.fakeThreeZero);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");

    }

    @Test
    @DisplayName("В кредит: Проверка некорректного заполнения CVC (две цифры)")
    void checkWrongCVc_1_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                invalidData.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Неверный формат");

    }

    @Test
    @DisplayName("В кредит: Проверка некорректного заполнения CVC (спецсимволы)")
    void checkWrongCVc_2_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                fakeData.fakeSymb);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");

    }

    @Test
    @DisplayName("В кредит: Проверка некорректного заполнения CVC (латиница)")
    void checkWrongCVc_3_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                fakeData.fakeWordEn);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");

    }

    @Test
    @DisplayName("В кредит: Проверка некорректного заполнения CVC (кириллица)")
    void checkWrongCVc_4_Credit() {
        purchaseTour.clickCredit();
        enteringForm(validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                fakeData.fakeWordRU);
        purchaseTour.clickContinueButton();
        purchaseTour.findErrorMessage("Поле обязательно для заполнения");

    }


    @Test
    @DisplayName("В кредит: Проверка покупки с валидной картой и проверка записи в БД")
    void checkHappyPathByCard_1_Credit() {
        purchaseTour.clickCredit();
        enteringForm(
                validDataWithCardApproved.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.successBuy();
        sqlHelper.getStatusCredit();
        assertEquals("APPROVED", sqlHelper.getStatusCredit());

    }

    @Test
    @DisplayName("В кредит: Проверка покупки с невалидной картой и проверка записи в БД")
    void checkHappyPathByCard_2_Credit() {
        purchaseTour.clickCredit();
        enteringForm(
                validDataWithCardDeclined.cardNumber,
                validDataWithCardApproved.moth,
                validDataWithCardApproved.year,
                validDataWithCardApproved.cardHolder,
                validDataWithCardApproved.cvc);
        purchaseTour.clickContinueButton();
        purchaseTour.rejected();
        sqlHelper.getStatusCredit();
        assertEquals("DECLINED", sqlHelper.getStatusCredit());

    }


}