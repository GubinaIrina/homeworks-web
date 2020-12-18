package ru.netology;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class OrderCardTest {

    @BeforeEach
    void openWebPage() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("Василий Иванов");
        form.$("[data-test-id='phone'] input").setValue("+79270000000");
        form.$("[data-test-id='agreement']").click();
        form.$("button").click();

        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotSubmitRequestByName() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("Vasiliy Иванов");
        form.$("[data-test-id='phone'] input").setValue("+79270000000");
        form.$("[data-test-id='agreement']").click();
        form.$("button").click();

        $("[data-test-id='name'] span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitRequestByPhone() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("Василий Иванов");
        form.$("[data-test-id='phone'] input").setValue("+792700000001");
        form.$("[data-test-id='agreement']").click();
        form.$("button").click();

        $("[data-test-id='phone'] span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitRequestByCheckBox() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("Василий Иванов");
        form.$("[data-test-id='phone'] input").setValue("+79270000000");
        form.$("button").click();

        $(".input_invalid>.checkbox_box");
    }

    @Test
    void shouldNotSubmitRequestByEmptyFieldsName() {
        SelenideElement form = $("form");
        form.$("[data-test-id='phone'] input").setValue("+79270000000");
        form.$("[data-test-id='agreement']").click();
        form.$("button").click();

        $("[data-test-id='name'] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitRequestByEmptyFieldsPhone() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("Василий Иванов");
        form.$("[data-test-id='agreement']").click();
        form.$("button").click();

        $("[data-test-id='phone'] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitRequestByEmptyFields() {
        SelenideElement form = $("form");
        form.$("button").click();

        $("[data-test-id='name'] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
