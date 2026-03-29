package io.github.cursodsousa.libraryapi.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        return ds;
    }

    @Bean
    public DataSource hikariDataSource(){
        HikariDataSource config = new HikariDataSource();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); //Maximo de conexões liberados
        config.setMinimumIdle(1); // Tamanho inicial do Pool;
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000);
        config.setConnectionTimeout(100000);
        config.setConnectionInitSql("select 1"); // teste de banco com um query

        return new HikariDataSource(config);
    }
}
