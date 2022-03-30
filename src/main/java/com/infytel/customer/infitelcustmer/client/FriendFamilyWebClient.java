package com.infytel.customer.infitelcustmer.client;

import com.infytel.customer.infitelcustmer.dto.CustomerDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;


@Component
public class FriendFamilyWebClient {

    @Autowired
    @Qualifier("friendFamily")
    private WebClient webClient;
    @CircuitBreaker(name="friendFamilyMS", fallbackMethod = "getFriendsFallback")
    public Flux<Long> getFriends(CustomerDTO customerDTO) {
        return webClient.get()
                .uri("/friends/"+customerDTO.getPhoneNo())
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(Long.class);
    }

    public Flux<Long> getFriendsFallback(CustomerDTO customerDTO, java.lang.Throwable e) {
        return Flux.fromIterable(new ArrayList<>());
    }
}
