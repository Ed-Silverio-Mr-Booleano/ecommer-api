package ao.bilabila.ecommerce.api.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Todos os endpoints
                        .allowedOrigins("*") // Qualquer origem
                        .allowedMethods("*") // Todos os métodos (GET, POST, PUT, DELETE, etc.)
                        .allowedHeaders("*"); // Todos os headers
            }
        };
    }
}

