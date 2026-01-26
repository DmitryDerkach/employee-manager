package config; // или com.example.config

import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocConfiguration;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springdoc.core.SwaggerUiOAuthProperties;
import org.springdoc.webmvc.core.SpringDocWebMvcConfiguration;
import org.springdoc.webmvc.ui.SwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // <--- Включает "Глобус" (поддержку контроллеров, JSON и т.д.)
@ComponentScan("controller") // <--- Говорим искать Контроллеры в папке controller
@Import({SpringDocConfiguration.class, SpringDocWebMvcConfiguration.class, SwaggerConfig.class})
public class WebConfig implements WebMvcConfigurer {
    // Здесь можно настраивать всякие штуки типа CORS, но пока оставим пустым

    // 1. Создаем объект базовых настроек
    @Bean
    public SpringDocConfigProperties springDocConfigProperties() {
        return new SpringDocConfigProperties();
    }

    // 2. Создаем объект настроек UI (передаем в него базовые настройки)
    @Bean
    public SwaggerUiConfigProperties swaggerUiConfigProperties() {
        return new SwaggerUiConfigProperties(); // <-- Пустые скобки!
    }

    // 3. Настройки OAuth (ЭТОГО НЕ ХВАТАЛО) 👇
    @Bean
    public SwaggerUiOAuthProperties swaggerUiOAuthProperties() {
        return new SwaggerUiOAuthProperties();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // 2. А ВОТ ЭТО НУЖНО ДОБАВИТЬ ДЛЯ SWAGGER 👇
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Разрешаем доступ к главной странице Swagger
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        // Разрешаем доступ к скриптам и стилям (webjars)
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
