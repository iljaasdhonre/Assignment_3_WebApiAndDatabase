package com.richieandmod.assignment_3_webapianddatabase.Config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;

@Configuration
public class MainConfig {

    @Bean
    public BasicDataSource dataSource() throws URISyntaxException {
//        String dbUrl = System.getenv("postgresql://localhost:5432/moviedb?currentSchema=public");
//        String username = System.getenv("postgres");
//        String password = System.getenv("supersecretpassword");

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/moviedb?currentSchema=public");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("supersecretpassword");

        return basicDataSource;
    }
}
