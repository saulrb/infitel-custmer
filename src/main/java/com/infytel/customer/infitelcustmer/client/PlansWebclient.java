package com.infytel.customer.infitelcustmer.client;

import com.infytel.customer.infitelcustmer.dto.CustomerDTO;
import com.infytel.customer.infitelcustmer.dto.PlanDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PlansWebclient {

    @Autowired
    @Qualifier("plans")
    WebClient webClient;
    @CircuitBreaker(name="plansMS" , fallbackMethod = "getPlanFallback")
    public Mono<PlanDTO> getPlan(CustomerDTO customerDTO){
        return webClient.get().uri("/plans/"+customerDTO.getCurrentPlan().getPlanId())
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(PlanDTO.class);

    }

    public Mono<PlanDTO> getPlanFallback(CustomerDTO customerDTO,Exception e) {
        return Mono.just(new PlanDTO());
    }
}
