package com.danyarov.library.config;

import com.danyarov.library.dao.BookDao;
import com.danyarov.library.dao.OrderDao;
import com.danyarov.library.dao.UserDao;
import com.danyarov.library.dao.impl.BookDaoImpl;
import com.danyarov.library.dao.impl.OrderDaoImpl;
import com.danyarov.library.dao.impl.UserDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * Main application configuration
 */
@Configuration
@ComponentScan(basePackages = "com.danyarov.library")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoImpl();
    }

    @Bean
    public BookDao bookDao() {
        return new BookDaoImpl();
    }

    @Bean
    public OrderDao orderDao() {
        return new OrderDaoImpl();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        resolver.setCookieName("locale");
        resolver.setCookieMaxAge(3600);
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }
}
