package ru.netology.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class CardRegistrationTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldRegisterWithFourDaysDelay() {
        open("http://localhost:9999");

        String planningDate = generateDate(4);

        $("[data-test-id='city'] input").setValue("Ижевск");

        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.CONTROL, "a"))
                .press(Keys.BACK_SPACE)
                .setValue(planningDate);


        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79001112233");
        $("[data-test-id='agreement']").click();

        $$("button").find(exactText("Забронировать")).click();

        $("[data-test-id='notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно забронирована на " + planningDate));
    }
}

