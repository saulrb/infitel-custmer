package com.infytel.customer.infitelcustmer.controller;

import com.infytel.customer.infitelcustmer.dto.CustomerDTO;
import com.infytel.customer.infitelcustmer.dto.LoginDTO;
import com.infytel.customer.infitelcustmer.service.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCustomer(@RequestBody CustomerDTO customerDTO){
        logger.info("Creation request for customer {} ",customerDTO);
        customerService.createCustomer(customerDTO);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean login(@RequestBody @NotNull LoginDTO loginDTO) {
        logger.info("Login request for customer {} with password {}", loginDTO.getPhoneNo(),loginDTO.getPassword());
        return customerService.login(loginDTO);
    }

    @GetMapping(value = "/customers/{phoneNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerDTO> getCustomerProfile(@PathVariable Long phoneNo){
        logger.info("Profile request for customer {}", phoneNo);
        return customerService.getCustomerProfile(phoneNo);
    }
}
