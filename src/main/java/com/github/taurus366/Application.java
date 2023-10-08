package com.github.taurus366;

import com.github.taurus366.model.service.UserRepository;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.theme.Theme;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
//@EnableWebSocket
@SpringBootApplication
//@NpmPackage(value = "@fontsource/arimo", version = "4.5.0")
@Theme(value = "homeparking")
@Push()
@EnableScheduling
//@WebServlet(name = "springServlet", urlPatterns = "/* ", asyncSupported = true, initParams = {
//        @WebInitParam(name = "org.atmosphere.cpr.broadcaster.shareableThreadPool", value = "true"),
//        @WebInitParam(name = "org.atmosphere.cpr.broadcaster.maxProcessingThreads", value = "2"),
//        @WebInitParam(name = "org.atmosphere.cpr.broadcaster.maxAsyncWriteThreads", value = "2"),
//        @WebInitParam(name = "org.atmosphere.cpr.maxSchedulerThread", value = "2")
//})
//@VaadinServletConfiguration ( ui = MyUI.class, productionMode = false )
//@EnableVaadin
@EnableVaadin({"com.github.taurus366", "org.parking.system", "org.system.shared"})
//@PWA(name = "Web Push", shortName = "Push")
@ComponentScan(basePackages = {"com.github.taurus366", "org.parking.system", "org.system.shared"})
@EnableJpaRepositories(basePackages = {"com.github.taurus366", "org.parking.system", "org.system.shared"})
@EntityScan(basePackages = {"com.github.taurus366", "org.parking.system", "org.system.shared"})
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(DataSource dataSource,
            SqlInitializationProperties properties, UserRepository repository) {
        // This bean ensures the database is only initialized when empty
        return new SqlDataSourceScriptDatabaseInitializer(dataSource, properties) {
            @Override
            public boolean initializeDatabase() {
                if (repository.count() == 0L) {
                    return super.initializeDatabase();
                }
                return false;
            }
        };
    }
}
