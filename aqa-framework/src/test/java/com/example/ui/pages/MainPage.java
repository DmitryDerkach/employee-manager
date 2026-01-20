package com.example.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {

    // === ЛОКАТОРЫ (То, что видит пользователь) ===
    // Мы прячем их здесь, чтобы тест не знал про css селекторы
    private final SelenideElement header = $("h2");
    private final SelenideElement addButton = $("button.btn-primary");
    private final ElementsCollection employeeRows = $$("tr");

    // === ДЕЙСТВИЯ (Что можно сделать на странице) ===

    public void openPage() {
        open("/");
    }

    public void clickAddButton() {
        addButton.shouldBe(visible).click();
    }

    // Проверки (Assertions) тоже часто выносят в Page Object
    public void checkHeaderVisible() {
        header.shouldHave(text("Employee Manager"));
    }

    public void checkLastEmployee(String expectedName, String expectedEmail) {
        // Берем последнюю строку и проверяем, что в ней есть имя и почта
        employeeRows.last().shouldHave(text(expectedName), text(expectedEmail));
    }
}