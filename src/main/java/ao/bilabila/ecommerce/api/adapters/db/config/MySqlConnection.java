package ao.bilabila.ecommerce.api.adapters.db.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class MySqlConnection {

    @Value("${spring.datasource.url}")
    private String uri;

    @Value("${spring.datasource.username}")
    private String usr;

    @Value("${spring.datasource.password}")
    private String pwd;

    @Bean
    public DriverManagerDataSource dataSource() {
        System.out.println("URL: " + uri);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(uri);
        dataSource.setUsername(usr);
        dataSource.setPassword(pwd);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}