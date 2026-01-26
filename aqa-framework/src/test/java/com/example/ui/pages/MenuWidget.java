package com.example.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

// Это не Страница, это Элемент (Компонент)
public class MenuWidget {

    private final SelenideElement homeLink = $("#nav-home");
    private final SelenideElement aboutLink = $("#nav-about");

    public void clickHome() {
        homeLink.shouldBe(visible).click();
    }

    public void clickAbout() {
        aboutLink.shouldBe(visible).click();
    }
}