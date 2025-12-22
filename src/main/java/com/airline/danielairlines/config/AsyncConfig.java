package com.airline.danielairlines.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {
    // Esta configuración habilita el procesamiento asíncrono
    // para los métodos anotados con @Async
}