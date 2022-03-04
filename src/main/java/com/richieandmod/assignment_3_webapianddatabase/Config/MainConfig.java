package com.richieandmod.assignment_3_webapianddatabase.Config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class MainConfig {
    @Value("#{systemEnvironment['DATABASE_URL']}")
    String databaseUrl;

    @Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        URI databaseUri = new URI(databaseUrl);
        String[] creds = databaseUri.getUserInfo().split(":");
        String url = String.format("jdbc:postgresql://%s:%s%s?sslmode=require&user=%s&password=%s",
                databaseUri.getHost(),
                databaseUri.getPort(),
                databaseUri.getPath(),
                creds[0],
                creds[1]);

        BasicDataSource source = new BasicDataSource();
        source.setUrl(url);

        return source;

    }
}
