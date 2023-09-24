package com.github.taurus366.config;


import com.github.taurus366.uiupdate.Updater;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {


    @Bean
    Updater updater() {
        return new Updater();
    }

}
