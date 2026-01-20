package com.example.ui.tests;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class EmployeeUiTest {

    @BeforeClass
    public void setUp() {
        Configuration.baseUrl = "http://localhost:8080";
        Configuration.browser = "chrome";
        Configuration.timeout = 5000;
    }

    @Test
    public void testApplicationOpens() {
        open("/");
        $("h2").shouldBe(visible).shouldHave(text("Employee Manager"));
        $("button.btn-primary").shouldBe(visible).shouldHave(text("Добавить"));
    }

    @Test
    public void testCanCreateEmployeeViaUi() {
        // 1. Открываем сайт
        open("/");

        // 2. Генерируем уникальные данные
        long timestamp = System.currentTimeMillis();
        String name = "SelenideUser";
        String email = "auto_" + timestamp + "@test.com";

        // 3. Нажимаем кнопку "+ Добавить"
        $("button.btn-primary").click();

        // 4. Ждем появления модального окна и заполняем поля
        // Selenide сам подождет, пока окно станет visible
        $("#firstName").setValue(name);
        $("#lastName").setValue("Robot");
        $("#email").setValue(email);
        $("#department").setValue("AQA");
        $("#salary").setValue("9999");

        // 5. Жмем "Сохранить" внутри модалки
        // Ищем кнопку по тексту, так надежнее
        $$("button").findBy(text("Сохранить")).click();

        // 6. ПРОВЕРКА:
        // Модальное окно должно исчезнуть
        $("#addEmployeeModal").shouldNotBe(visible);

        // В таблице должна появиться новая строка с нашим email
        // $$("tr") - это все строки. Берем последнюю и проверяем текст
        $$("tr").last().shouldHave(text(name), text(email));
    }
}