package ru.edu.module12.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "ru.edu.module12.repository")
@EnableTransactionManagement
public class Config {
    private static Logger LOGGER = LoggerFactory.getLogger(Config.class);
    @Bean
    public DataSource dataSource() {
        try {
            var dbBuilder = new EmbeddedDatabaseBuilder();
            return dbBuilder.setType(EmbeddedDatabaseType.H2)
                    .build();
        } catch (Exception e) {
            LOGGER.error("Embedded DataSource bean cannot be created!", e);
            return null;
        }
    }
}