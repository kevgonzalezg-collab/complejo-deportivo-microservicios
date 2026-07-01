package com.reservas.msreservas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient seguridadWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8091/api/auth").build();
    }

    @Bean
    public WebClient listaNegraWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8086/api/listanegra").build();
    }

    @Bean
    public WebClient usuariosWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8084/api/usuarios").build();
    }

    @Bean
    public WebClient canchasWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8082/api/canchas").build();
    }

    @Bean
    public WebClient notificacionesWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8085/api/notificaciones").build();
    }

    @Bean
    public WebClient estadisticasWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8095/api/estadisticas").build();
    }
}