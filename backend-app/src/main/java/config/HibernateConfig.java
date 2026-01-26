package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

// @Configuration — говорит Спрингу: "В этом классе лежат настройки бинов"
@Configuration
// @EnableTransactionManagement — разрешает Спрингу управлять транзакциями (откатывать изменения при ошибках)
@EnableTransactionManagement
@ComponentScan({"dao", "service"})
// Если потом добавишь сервисы, напишешь: {"dao", "service"}
public class HibernateConfig {

    // 1. Bean DataSource (Источник данных)
    // Это настройки подключения: куда стучаться, какой логин/пароль.
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        // Драйвер для PostgreSQL (тот самый, что мы качали в pom.xml)
//        dataSource.setDriverClassName("org.postgresql.Driver");
//
//        // Адрес базы.
//        // localhost:5432 — твой комп и порт.
//        // /postgres — имя базы данных по умолчанию (мы пока используем её).
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
//
//        // ТВОЙ ЛОГИН И ПАРОЛЬ ОТ POSTGRES
//        dataSource.setUsername("postgres"); // Стандартный логин
//        dataSource.setPassword("1111");     // <--- ПРОВЕРЬ ПАРОЛЬ! (1234 или root)
//
//        return dataSource;
//    }

    @Bean
    public DataSource dataSource() {
// 1. Bean DataSource (Источник данных)
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // Драйвер остается прежним
        dataSource.setDriverClassName("org.postgresql.Driver");

        // ---------------------------------------------------------
        // МАГИЯ ГИБКОЙ КОНФИГУРАЦИИ 👇
        // ---------------------------------------------------------

        // 1. URL БАЗЫ ДАННЫХ
        // Пытаемся получить адрес от Docker Compose
        String dbUrl = System.getenv("JDBC_URL");

        if (dbUrl != null) {
            // Если мы в Докере — ставим адрес оттуда
            dataSource.setUrl(dbUrl);
        } else {
            // Если мы локально (переменной нет) — ставим как было раньше
            dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        }

        // 2. ПОЛЬЗОВАТЕЛЬ
        String dbUser = System.getenv("JDBC_USER");
        if (dbUser != null) {
            dataSource.setUsername(dbUser);
        } else {
            dataSource.setUsername("postgres"); // Твой локальный логин
        }

        // 3. ПАРОЛЬ
        String dbPassword = System.getenv("JDBC_PASSWORD");
        if (dbPassword != null) {
            dataSource.setPassword(dbPassword);
        } else {
            dataSource.setPassword("1111"); // Твой локальный пароль
        }

        return dataSource;
    }

    // 2. Bean SessionFactory (Фабрика сессий)
    // Это главный инструмент Hibernate. Он создает сессии для работы с базой.
    // Мы скармливаем ему настройки подключения (dataSource) и говорим, где искать Сущности.
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // Подключаем источник данных, созданный выше
        sessionFactory.setDataSource(dataSource());

        // В какой папке лежат наши Entity (@Table)?
        // ВАЖНО: Убедись, что твоя Employee лежит именно в этом пакете!
        sessionFactory.setPackagesToScan("model");

        // Дополнительные настройки Hibernate
        Properties hibernateProperties = new Properties();
        // Диалект помогает Hibernate генерировать правильный SQL именно для PostgreSQL
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        // Показывать SQL-запросы в консоли (чтобы мы видели, что происходит)
        hibernateProperties.put("hibernate.show_sql", "true");
        // Красиво форматировать SQL (чтобы удобно читать)
        hibernateProperties.put("hibernate.format_sql", "true");
        // Автоматически создавать таблицы! (UPDATE — обновит структуру, если она изменилась)
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");

        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    // 3. Bean TransactionManager (Менеджер транзакций)
    // Он следит, чтобы операции выполнялись целиком или не выполнялись вообще.
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
