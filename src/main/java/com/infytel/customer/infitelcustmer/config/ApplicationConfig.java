package com.infytel.customer.infitelcustmer.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return  new ModelMapper();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create().responseTimeout(Duration.of(200, ChronoUnit.MILLIS)).protocol(HttpProtocol.HTTP11);
    }

    @Bean
    public ClientHttpConnector clientHttpConnector(){
        return new ReactorClientHttpConnector(httpClient());
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder builder() {
        return WebClient.builder();
    }

    @Bean
    @Qualifier("friendFamily")
    public WebClient webClient(WebClient.Builder builder) {
        return builder.clientConnector(clientHttpConnector()).baseUrl("http://FRIENDFAMILYMS").build();
    }

    @Bean
    @Qualifier("plans")
    public WebClient webClientPlans(WebClient.Builder builder) {
        return builder.clientConnector(clientHttpConnector()).baseUrl("http://PLANMS").build();
    }

}
