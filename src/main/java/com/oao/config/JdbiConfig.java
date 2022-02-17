package com.oao.config;

import com.oao.repository.HousesManagementDAO;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JdbiConfig {

    @Bean
    public DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    Jdbi jdbi() {
        Jdbi jdbi = Jdbi.create(dataSource());
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Bean
    HousesManagementDAO housesManagementDAO(Jdbi jdbi) {
        return jdbi.onDemand(HousesManagementDAO.class);
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }
}
