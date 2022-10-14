package ru.job4j.cinema.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:db.properties")
public class DataSourceConfig {

    @Bean
    public BasicDataSource loadPool(@Value("${jdbc.url}") String url,
                                    @Value("${jdbc.driver}") String driver,
                                    @Value("${jdbc.username}") String username,
                                    @Value("${jdbc.password}") String password) {
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(driver);
        pool.setUrl(url);
        pool.setUsername(username);
        pool.setPassword(password);
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }

}