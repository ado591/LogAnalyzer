package ru.angela.NauJava.configurations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.angela.NauJava.entities.Log;

@Configuration
public class LogConfig
{
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Log> logList() {
        return new ArrayList<>();
    }
}

