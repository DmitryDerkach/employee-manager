package com.example;

import org.testcontainers.containers.GenericContainer;
import org.testng.annotations.Test;

public class DockerSanityTest {

    @Test
    public void testDockerIsWorking() {
        System.out.println("⏳ Пытаемся подключиться к Docker...");

        // Самый простой тест: запускаем крошечный Linux (Alpine)
        // и просим его сказать "Hello"
        try (GenericContainer<?> container = new GenericContainer<>("alpine:latest")
                .withCommand("echo 'Hello from Docker!'")) {

            container.start();

            System.out.println("✅ УРА! Docker работает. Логи контейнера:");
            System.out.println(container.getLogs());

        } catch (Exception e) {
            System.err.println("❌ Ошибка подключения:");
            e.printStackTrace();
            throw e; // Падаем громко
        }
    }
}
