package ru.aizen.app;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootConfiguration
@ComponentScan("ru.aizen")
public class AppConfig {

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

}
