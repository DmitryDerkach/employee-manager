package config;

public class TestConfig {

    // ЭТО НАШ ПЕРЕКЛЮЧАТЕЛЬ
    // Логика такая:
    // 1. Мы просим систему: "Дай мне переменную base.url".
    // 2. Если мы передали ее через консоль (Maven) — берется она.
    // 3. Если НЕ передали (запустили просто кнопкой в IDEA) — берется "http://localhost:8080" (по умолчанию).

    public static final String BASE_URL = System.getProperty("base.url", "http://localhost:8080");

}