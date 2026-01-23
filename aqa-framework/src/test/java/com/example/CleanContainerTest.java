package com.example;

import config.TestContainersConfig;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static org.testng.Assert.assertEquals;

// Наследуемся от конфига, чтобы сработали @BeforeSuite и @AfterSuite
public class CleanContainerTest extends TestContainersConfig {

    @Test
    public void testApplicationIsRunning() {
        // Просто открываем главную страницу
        // Base URL уже настроен в TestContainersConfig!
        open("/");

        // Проверяем заголовок (из твоего HTML)
        assertEquals(title(), "Employee Manager");

        System.out.println("🎉 УРА! Тест прошел внутри одноразового контейнера!");
    }
}