package com.example.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.equalTo;

public class WireMockLearningTest {

    // Создаем объект сервера WireMock (он будет висеть на порту 8089)
    private WireMockServer wireMockServer;

    @BeforeClass
    public void setup() {
        // 1. Инициализируем и запускаем сервер
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();

        // 2. Настраиваем "Мок" (Stub)
        // Говорим: "Если придет GET запрос на адрес /external/salary..."
        wireMockServer.stubFor(get(urlEqualTo("/external/salary"))
                // "...то ответь статусом 200 и JSON-ом { "amount": 5000 }"
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("{\"status\": \"ok\", \"amount\": 5000}")));
    }

    @Test
    public void testWireMockIsWorking() {
        // 3. Проверяем, как это работает через RestAssured
        // Представь, что это наше приложение стучится во внешний сервис

        RestAssured
                .given()
                .baseUri("http://localhost:8089") // Стучимся именно в WireMock!
                .when()
                .get("/external/salary")
                .then()
                .statusCode(200)
                .body("amount", equalTo(5000))
                .body("status", equalTo("ok"));

        System.out.println("✅ WireMock успешно ответил нам поддельными данными!");
    }

    @AfterClass
    public void teardown() {
        // 4. Обязательно останавливаем сервер после тестов
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}