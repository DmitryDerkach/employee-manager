package com.example.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class EmployeeModal {

    // Локаторы полей ввода
    private final SelenideElement modalWindow = $("#addEmployeeModal");
    private final SelenideElement firstNameInput = $("#firstName");
    private final SelenideElement lastNameInput = $("#lastName");
    private final SelenideElement emailInput = $("#email");
    private final SelenideElement departmentInput = $("#department");
    private final SelenideElement salaryInput = $("#salary");

    // Кнопку ищем по тексту, так надежнее
    private final SelenideElement saveButton = $$("button").findBy(text("Сохранить"));

    // Метод, который заполняет ВСЮ форму сразу
    // Это удобнее, чем вызывать 5 методов в тесте
    public void fillForm(String firstName, String lastName, String email, String department, String salary) {
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);
        emailInput.setValue(email);
        departmentInput.setValue(department);
        salaryInput.setValue(salary);
    }

    public void clickSave() {
        saveButton.click();
    }

    public void checkIsClosed() {
        modalWindow.shouldNotBe(visible);
    }
}