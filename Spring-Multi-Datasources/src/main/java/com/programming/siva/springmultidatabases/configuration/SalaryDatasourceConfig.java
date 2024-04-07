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

import com.programming.siva.springmultidatabases.salary.Employee;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.programming.siva.springmultidatabases.salary",
						entityManagerFactoryRef="salaryEntityManagerFactory",
						transactionManagerRef="salaryTransactionManager")
public class SalaryDatasourceConfig {
	
	@Bean
    @ConfigurationProperties("spring.datasource.salary")
    DataSourceProperties salaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.salary.configuration")
    DataSource salaryDataSource() {
        return salaryDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    
    @Bean(name = "salaryEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean salaryEntityManagerFactory(
                EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(salaryDataSource())
                .packages(Employee.class)
                .build();
    }
		

    @Bean(name = "salaryTransactionManager")
    PlatformTransactionManager securityTransactionManager(
            final @Qualifier("salaryEntityManagerFactory") 
			LocalContainerEntityManagerFactoryBean salaryEntityManagerFactory) {
        return new JpaTransactionManager(salaryEntityManagerFactory.getObject());
	}

}
