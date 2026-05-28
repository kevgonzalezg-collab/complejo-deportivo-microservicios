package com.canchas.inventarioms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient estadisticasWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8095/api/estadisticas")
                .build();
    }
}