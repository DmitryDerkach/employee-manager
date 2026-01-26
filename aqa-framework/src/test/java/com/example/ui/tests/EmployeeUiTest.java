package com.example.ui.tests;

import com.codeborne.selenide.Configuration;
import com.example.ui.pages.AboutPage;
import com.example.ui.pages.EmployeeModal;
import com.example.ui.pages.MainPage;
import config.TestConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EmployeeUiTest {

    // Объявляем наши страницы
    private MainPage mainPage = new MainPage();
    private EmployeeModal modal = new EmployeeModal();
    private AboutPage aboutPage = new AboutPage();

    @BeforeClass
    public void setUp() {
        Configuration.baseUrl = TestConfig.BASE_URL;
        Configuration.browser = "chrome";
        Configuration.timeout = 5000;
    }

    @Test
    public void testApplicationOpens() {
        mainPage.openPage();
        mainPage.checkHeaderVisible();
    }

    @Test
    public void testCanCreateEmployeeViaUi() {
        // Подготовка данных
        long timestamp = System.currentTimeMillis();
        String name = "PageObjectUser";
        String email = "po_" + timestamp + "@test.com";

        // 1. Открываем страницу
        mainPage.openPage();

        // 2. Жмем кнопку
        mainPage.clickAddButton();

        // 3. Заполняем форму (Смотри, как чисто стало!)
        modal.fillForm(name, "Bot", email, "DevOps", "5000");
        modal.clickSave();

        // 4. Проверяем результат
        modal.checkIsClosed();
        mainPage.checkLastEmployee(name, email);
    }

    @Test
    public void testNavigationMenu() {
        // 1. Открываем главную
        mainPage.openPage();
        mainPage.checkHeaderVisible();

        // 2. Используем ВИДЖЕТ для перехода на "О проекте"
        mainPage.menu.clickAbout();

        // 3. Проверяем, что мы на странице "О проекте"
        aboutPage.checkHeader();

        // 4. Используем ВИДЖЕТ (уже со страницы About), чтобы вернуться
        aboutPage.menu.clickHome();

        // 5. Проверяем, что вернулись
        mainPage.checkHeaderVisible();
    }
}