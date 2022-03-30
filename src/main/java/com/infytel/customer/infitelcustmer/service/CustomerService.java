package com.infytel.customer.infitelcustmer.service;

import com.infytel.customer.infitelcustmer.dto.CustomerDTO;
import com.infytel.customer.infitelcustmer.dto.LoginDTO;
import reactor.core.publisher.Mono;

public interface CustomerService {

    void createCustomer(CustomerDTO customerDTO);
    boolean login(LoginDTO loginDTO);
    Mono<CustomerDTO> getCustomerProfile(Long phoneNo);

}
