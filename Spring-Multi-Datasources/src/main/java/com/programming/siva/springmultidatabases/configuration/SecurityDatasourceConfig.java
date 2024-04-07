package com.programming.siva.springmultidatabases.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.programming.siva.springmultidatabases.security.User;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.programming.siva.springmultidatabases.security",
						entityManagerFactoryRef="securityEntityManagerFactory",
						transactionManagerRef="securityTransactionManager")
public class SecurityDatasourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.security")
    DataSourceProperties securityDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.security.configuration")
    DataSource securityDataSource() {
        return securityDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }


    @Bean
    EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        return new EntityManagerFactoryBuilder(vendorAdapter, new HashMap<>(), null);
    }


    @Bean(name = "securityEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean securityEntityManagerFactory(
                EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(securityDataSource())
                .packages(User.class)
                .build();
    }
		

    @Bean(name = "securityTransactionManager")
    PlatformTransactionManager securityTransactionManager(
            final @Qualifier("securityEntityManagerFactory") 
			LocalContainerEntityManagerFactoryBean securityEntityManagerFactory) {
        return new JpaTransactionManager(securityEntityManagerFactory.getObject());
	    }
}
