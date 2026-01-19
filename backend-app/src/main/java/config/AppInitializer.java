package config; // или com.example.config

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // 1. Метод, указывающий на конфигурацию "Склада" (Бэкенд: БД, Сервисы, DAO)
    // Эти бины видны всему приложению.
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{HibernateConfig.class};
    }

    // 2. Метод, указывающий на конфигурацию "Ресепшена" (Веб: Контроллеры, ViewResolver)
    // Эти бины касаются только обработки HTTP-запросов.
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    // 3. На какой адрес реагировать?
    // "/" означает "Перехватывать ВСЕ входящие запросы на этот сервер".
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
