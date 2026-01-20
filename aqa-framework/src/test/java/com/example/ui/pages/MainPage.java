package com.example.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {

    // Внедряем (композиция) наше меню
    // Теперь через mainPage.menu().clickAbout() мы можем кликать
    public final MenuWidget menu = new MenuWidget();

    private final SelenideElement header = $("h2");
    private final SelenideElement addButton = $("button.btn-primary");
    private final ElementsCollection employeeRows = $$("tr");

    public void openPage() {
        open("/index.html"); // Явно указываем index.html
    }

    public void checkHeaderVisible() {
        header.shouldHave(text("Employee Manager"));
    }
    
    // ... остальные методы (clickAddButton и т.д.) оставь как были
    public void clickAddButton() {
        addButton.shouldBe(visible).click();
    }
    
    public void checkLastEmployee(String expectedName, String expectedEmail) {
        employeeRows.last().shouldHave(text(expectedName), text(expectedEmail));
    }
}