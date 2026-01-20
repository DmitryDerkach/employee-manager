package config; // или com.example.config

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // <--- Включает "Глобус" (поддержку контроллеров, JSON и т.д.)
@ComponentScan("controller") // <--- Говорим искать Контроллеры в папке controller
public class WebConfig implements WebMvcConfigurer {
    // Здесь можно настраивать всякие штуки типа CORS, но пока оставим пустым
	
	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
