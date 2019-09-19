package com.wityo.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableConfigurationProperties(DBConfiguration.class)
@ConfigurationProperties("spring.datasource")
public class DBConfiguration {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Profile("build")
    @Bean
    public String buildDatabaseConnection() {
        System.out.println("DB connection for BUILD - H2");
        System.out.println(driverClassName);
        System.out.println(url);
        return "DB connection for BUILD - H2";
    }

    @Profile("dev")
    @Bean
    public String devDatabaseConnection() {
        System.out.println("DB connection for DEV - mySQL");
        System.out.println(driverClassName);
        System.out.println(url);
        return "DB connection for DEV - mySQL";
    }


    @Profile("prod")
    @Bean
    public String prodDatabaseConnection() {
        System.out.println("DB Connection to RDS_PROD - High Performance Instance");
        System.out.println(driverClassName);
        System.out.println(url);
        return "DB Connection to RDS_PROD - High Performance Instance";
    }
}
