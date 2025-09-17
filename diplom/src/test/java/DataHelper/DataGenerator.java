package dataHelper;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static final Faker FAKER = new Faker();

    private DataGenerator() {
    }

    @Value
    public static class CardInfo {
        public String cardNumber;
        public String moth;
        public String year;
        public String cardHolder;
        public String cvc;

    }

    public static CardInfo getValidDataCard(String cardNumb) {
        String cardNumber = cardNumb;
        String month = getValidMonth();
        String year = LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        String cardHolder = FAKER.name().name().toUpperCase(Locale.forLanguageTag("eng"));
        String cvc = FAKER.numerify("###");
        return new CardInfo(cardNumber, month, year, cardHolder, cvc);

    }

    public static CardInfo getInvalidDataCard() {
        String cardNumber = FAKER.numerify("#");
        String moth = FAKER.numerify("#");
        String year = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        String cardHolder = FAKER.name().firstName().toUpperCase(Locale.forLanguageTag("ru"));
        String cvc = FAKER.numerify("##");
        return new CardInfo(cardNumber, moth, year, cardHolder, cvc);
    }

    @Value
    public static class FakeCardInfo {
        public String fakeNumber;
        public String fakeWordEn;
        public String fakeWordRU;
        public String fakeEmpty;
        public String fakeSymb;
        public String fakeAllZero;
        public String fakeMonth;
        public String fakeTwoZero;
        public String fakeThreeZero;

    }

    public static FakeCardInfo getFakeData() {
        String fakeNumber = FAKER.numerify("###############");
        String fakeWordEn = FAKER.bothify("FW");
        String fakerWordRU = FAKER.numerify("ДЮ");
        String fakeEmpty = FAKER.numerify("");
        String fakeSymb = FAKER.numerify("+=");
        String fakeAllZero = FAKER.numerify("0000000000000000");
        String fakeMonth = FAKER.numerify("13");
        String fakeTwoZero = FAKER.numerify("00");
        String fakeThreeZero = FAKER.numerify("000");
        return new FakeCardInfo(fakeNumber, fakeWordEn, fakerWordRU, fakeEmpty, fakeSymb, fakeAllZero, fakeMonth,
                fakeTwoZero, fakeThreeZero);
    }


    public static String getValidCardNumberApproved() {
        return "4444 4444 4444 4441";
    }

    public static String getValidCardNumberDeclined() {
        return "4444 4444 4444 4442";
    }

    public static String getValidMonth() {
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        Random index = new Random();
        int indexInt = index.nextInt(months.length);
        return months[indexInt];
    }

}
