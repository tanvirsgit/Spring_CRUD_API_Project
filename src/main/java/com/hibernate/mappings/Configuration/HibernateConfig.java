package com.hibernate.mappings.Configuration;

import com.hibernate.mappings.Interceptor.DemoInterceptor1;
import com.hibernate.mappings.Interceptor.DemoInterceptor2;
import com.hibernate.mappings.Interceptor.DemoInterceptor3;
import org.flywaydb.core.Flyway;
import org.hibernate.dialect.MySQL55Dialect;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.hibernate.mappings")
@EnableWebMvc
public class HibernateConfig implements WebMvcConfigurer {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/demo");
        dataSource.setUsername("root");
        dataSource.setPassword("Dipro@123456");

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver()); // For tomcat9
        }catch (SQLException s){}
        return dataSource;
    }

    @Bean
    //@DependsOn("flyway")
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(this.dataSource());
        sessionFactoryBean.setPackagesToScan(new String[]{"com.hibernate.mappings"});
        sessionFactoryBean.setHibernateProperties(this.properties());
        return sessionFactoryBean;
    }

    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", MySQL55Dialect.class.getName());
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql", "true");
        return properties;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(this.sessionFactoryBean().getObject());
        return transactionManager;
    }

    @Bean
    public Flyway flyway(){
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource())
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
        return flyway;
    }


   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DemoInterceptor1());
        registry.addInterceptor(new DemoInterceptor2());
        registry.addInterceptor(new DemoInterceptor3());
    }*/
}
