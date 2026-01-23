package config;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.MountableFile;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.nio.file.Paths;

public class TestContainersConfig {

    // 👇👇👇 ВСТАВЛЯТЬ ОТСЮДА 👇👇👇
//    static {
//        // ЖЕСТКАЯ ПРИВЯЗКА К ПОРТУ
//        // Мы говорим Java: "Не ищи ничего, иди сразу на порт 2375!"
//        // БЫЛО: "tcp://localhost:2375"
//        // СТАЛО: Используем IPv6 адрес, который точно работает у тебя 👇
//        System.setProperty("docker.host", "tcp://[::1]:2375");
//
//        // Отключаем проверку сертификатов (так как мы работаем локально без https)
//        System.setProperty("docker.tls.verify", "false");
//    }
    // 👆👆👆 ДО СЮДА 👆👆👆

    private static final Logger log = LoggerFactory.getLogger(TestContainersConfig.class);

    // 1. Создаем виртуальную сеть (как 'networks' в docker-compose)
    // Чтобы контейнеры видели друг друга
    private static final Network NETWORK = Network.newNetwork();

    // 2. Объявляем Базу Данных
    // Обрати внимание: мы даем ей сетевой алиас "db" — именно так к ней будет обращаться Tomcat
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:15")
            .withNetwork(NETWORK)
            .withNetworkAliases("db")
            .withDatabaseName("employee_db")
            .withUsername("postgres")
            .withPassword("1111");

    // 3. Объявляем Приложение (Tomcat)
    // Мы берем чистый Tomcat и "монтируем" в него наш WAR-файл
    private static final GenericContainer<?> APP = new GenericContainer<>("tomcat:9.0-jdk8-openjdk")
            .withNetwork(NETWORK)
            .withExposedPorts(8080) // Порт внутри контейнера
            .dependsOn(POSTGRES)    // Ждем, пока запустится БД
            .withEnv("JDBC_URL", "jdbc:postgresql://db:5432/employee_db") // Передаем настройки
            .withEnv("JDBC_USER", "postgres")
            .withEnv("JDBC_PASSWORD", "1111");

    @BeforeSuite
    public void startEnvironment() {
        log.info("🚀 Запускаем Testcontainers...");

        // A. Запускаем БД
        POSTGRES.start();
        log.info("✅ Postgres запущен!");

        // B. Находим наш WAR-файл на диске
        // Важно: Путь должен вести к файлу, который собрал Maven
        // Мы выходим из папки aqa-framework (..) и идем в backend-app
        File warFile = Paths.get("..", "backend-app", "target", "employee-app.war").toFile();

        if (!warFile.exists()) {
            throw new RuntimeException("❌ ОШИБКА: Файл " + warFile.getAbsolutePath() + " не найден! Сначала выполни 'mvn package'");
        }

        // C. Копируем WAR внутрь контейнера Tomcat
        APP.withCopyFileToContainer(
                MountableFile.forHostPath(warFile.getAbsolutePath()),
                "/usr/local/tomcat/webapps/ROOT.war"
        );

        // D. Запускаем Tomcat и ждем, пока он скажет "Server startup"
        APP.start();

        // Магия логов: выводим логи Tomcat прямо в консоль IDEA
        APP.followOutput(new Slf4jLogConsumer(log));

        // E. НАСТРОЙКА ТЕСТОВ
        // Самое главное: Testcontainers выдал случайный порт (например, 32145).
        // Мы должны сказать Selenide и RestAssured использовать именно его.
        Integer randomPort = APP.getMappedPort(8080);

        String dynamicUrl = "http://localhost:" + randomPort;
        log.info("🌍 Приложение доступно по адресу: " + dynamicUrl);

        Configuration.baseUrl = dynamicUrl;
        RestAssured.baseURI = dynamicUrl;
    }

    @AfterSuite
    public void stopEnvironment() {
        // Убираем за собой мусор
        APP.stop();
        POSTGRES.stop();
        log.info("🏁 Контейнеры уничтожены.");
    }
}