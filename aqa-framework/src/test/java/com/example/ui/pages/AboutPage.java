package com.example.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class AboutPage {

    // И тут тоже есть меню! Мы не дублируем код кликов.
    public final MenuWidget menu = new MenuWidget();

    private final SelenideElement header = $("h2");

    public void checkHeader() {
        header.shouldHave(text("О проекте Employee Manager"));
    }
}