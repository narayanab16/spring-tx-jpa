package com.narayana.jpa.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class AppConfig {
    @Bean
    public DataSource h2DataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.h2.Driver");
        hikariConfig.setUsername("sa");
        hikariConfig.setPassword("welcome1");
        hikariConfig.setPoolName("H2_db_Pool");
        hikariConfig.setMinimumIdle(3);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setAutoCommit(false);
        hikariConfig.setTransactionIsolation("TRANSACTION_READ_COMMITTED");
        //tcpServer mode
        //hikariConfig.setJdbcUrl("jdbc:h2:tcp://localhost:8888/mydb;TRACE_LEVEL_FIle=4");
        hikariConfig.setJdbcUrl("jdbc:h2:tcp://localhost:8888/mydb;TRACE_LEVEL_SYSTEM_OUT=3");
        hikariConfig.setJdbcUrl("jdbc:h2:tcp://localhost:8888/mydb");
        //Embed mode
        //hikariConfig.setJdbcUrl("jdbc:h2:~/mydb");

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(false);
        adapter.setShowSql(true);
        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setJpaVendorAdapter(adapter);
        managerFactoryBean.setPackagesToScan("com.narayana.jpa.model");
        managerFactoryBean.setDataSource(h2DataSource());
        Properties properties = new Properties();
        // TURN OFF BATCH UPDATES
        //properties.put("hibernate.jdbc.batch_size", 10);
        //properties.put("hibernate.order_inserts", true);
        //properties.put("hibernate.order_updates", true);
        //properties.put("hibernate.batch_versioned_data", true);
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        //properties.put("hibernate.generate_statistics", "true");
        managerFactoryBean.setJpaProperties(properties);
        return managerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

}
