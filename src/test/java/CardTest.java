import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class CardTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }
        @Test
        void sendCardAeliveryForm() {

            Selenide.open("http://localhost:9999/");
            SelenideElement formElement = $("form");
            formElement.$("[placeholder='Город']").setValue("Пенза");
            formElement.$("[name='name']").setValue("Петр Петров");
            String expectedDate = generateDate(3, "dd.MM.yyyy");
            formElement.$("[placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
            formElement.$("[placeholder='Дата встречи']").setValue(expectedDate);
            formElement.$("[name='phone']").setValue("+79112244668");
            formElement.$("label").click();
            $(withText("Забронировать")).click();
            $(Selectors.withText("Успешно")).should(Condition.visible, Duration.ofSeconds(15));
            $("[class='notification__content']").shouldHave(Condition.text(expectedDate)).shouldBe(Condition.visible);
        }
    }

