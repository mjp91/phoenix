package com.mpearsall.hr.config.jpa;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = {"com.mpearsall.hr.repository.primary"}
)
@Order(1)
public class PrimaryJpaConfig {
  @Primary
  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder entityManagerFactoryBuilder,
      @Qualifier("dataSource") DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean factoryBean = entityManagerFactoryBuilder.dataSource(dataSource)
        .packages("com.mpearsall.hr.entity.primary")
        .persistenceUnit("primary")
        .build();
    factoryBean.setJpaProperties(jpaProperties());
    factoryBean.afterPropertiesSet();

    return factoryBean;
  }

  @Primary
  @Bean(name = "transactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  public static Properties jpaProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
    properties.setProperty("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
    properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
    properties.setProperty(Environment.ENABLE_LAZY_LOAD_NO_TRANS, "true");

    return properties;
  }
}
