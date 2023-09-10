package com.parking.www.config;


import com.parking.www.uiupdate.Updater;
import com.vaadin.flow.shared.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {


    @Bean
    Updater updater() {
        return new Updater();
    }

}
