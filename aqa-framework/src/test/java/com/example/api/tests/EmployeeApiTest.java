package com.example.api.tests;

import com.example.api.model.EmployeeDto;
import config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class EmployeeApiTest {

    @BeforeClass
    public void setUp() {
        // Настраиваем RestAssured: говорим ему, где живет наш сервер
        //RestAssured.baseURI = "http://localhost:8080";
        RestAssured.baseURI = TestConfig.BASE_URL;
    }

    @Test
    public void testCanCreateEmployee() {
        // --- 1. Подготовка данных (Arrange) ---
        // Генерируем уникальный email, чтобы при повторном запуске тест не падал из-за дубликата
        String uniqueEmail = "test_" + System.currentTimeMillis() + "@example.com";

        EmployeeDto newEmployee = EmployeeDto.builder()
                .firstName("Auto")
                .lastName("Tester")
                .department("QA")
                .salary(1500.0)
                .email(uniqueEmail)
                .build();

        // --- 2. Действие (Act) - Отправляем POST ---
        given() // "Дано":
                .contentType(ContentType.JSON) // Я отправляю JSON
                .body(newEmployee)             // Вот тело запроса (Jackson сам превратит объект в JSON-строку)
                .when() // "Когда":
                .post("/employees")            // Делаю POST на этот путь
                .then() // "Тогда":
                .statusCode(200);              // Ожидаю статус 200 OK

        // --- 3. Проверка (Assert) - Проверяем через GET ---
        // Так как POST не вернул нам созданный объект, мы запрашиваем список всех
        List<EmployeeDto> employees = given()
                .when()
                .get("/employees")
                .then()
                .statusCode(200)
                .extract().body()
                // Магия RestAssured + Jackson: преврати JSON-ответ в список объектов EmployeeDto
                .jsonPath().getList(".", EmployeeDto.class);

        // Ищем нашего сотрудника в списке по email
        boolean isFound = employees.stream()
                .anyMatch(e -> e.getEmail().equals(uniqueEmail));

        Assert.assertTrue(isFound, "Сотрудник с email " + uniqueEmail + " не был найден в списке!");
    }
    
    @Test
    public void testCannotCreateEmployeeWithoutEmail() {
        // --- 1. Prepare (Генерируем сотрудника БЕЗ почты) ---
        EmployeeDto invalidEmployee = EmployeeDto.builder()
                .firstName("Buggy")
                .lastName("Bugov")
                .salary(1000.0)
                .department("QA")
                // .email(...) // СПЕЦИАЛЬНО НЕ ЗАПОЛНЯЕМ EMAIL
                .build();

        // --- 2. Act & Assert (Отправляем и ждем ошибку) ---
        given()
            .contentType(ContentType.JSON)
            .body(invalidEmployee)
        .when()
            .post("/employees")
        .then()
            .log().all() // Эта команда выведет ответ сервера в консоль (чтобы мы увидели ошибку)
            .statusCode(500); // Ожидаем ошибку сервера (Internal Server Error)
    }
}