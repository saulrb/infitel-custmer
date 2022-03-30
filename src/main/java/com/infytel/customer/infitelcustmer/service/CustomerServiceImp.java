package com.infytel.customer.infitelcustmer.service;

import com.infytel.customer.infitelcustmer.client.FriendFamilyWebClient;
import com.infytel.customer.infitelcustmer.client.PlansWebclient;
import com.infytel.customer.infitelcustmer.dto.CustomerDTO;
import com.infytel.customer.infitelcustmer.dto.LoginDTO;
import com.infytel.customer.infitelcustmer.entity.Customer;
import com.infytel.customer.infitelcustmer.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Service
public class CustomerServiceImp implements CustomerService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    CustomerRepository customerRepository;
    ModelMapper modelMapper;
    PlansWebclient plansWebclient;
    FriendFamilyWebClient friendFamilyWebClient;
    @Autowired
    public CustomerServiceImp(CustomerRepository customerRepository,
                              ModelMapper modelMapper,
                              PlansWebclient plansWebclient,
                              FriendFamilyWebClient friendFamilyWebClient){
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.plansWebclient =plansWebclient;
        this.friendFamilyWebClient = friendFamilyWebClient;
    }

    @Override
    public void createCustomer(CustomerDTO customerDTO) {
        logger.info("Creation request for customer {}", customerDTO);
        Customer customer = modelMapper.map(customerDTO,Customer.class);
        customerRepository.save(customer);
    }

    @Override
    public boolean login(LoginDTO loginDTO) {
        Boolean isLogged = false;
        logger.info("Login request for customer {} with password {}", loginDTO.getPhoneNo(),loginDTO.getPassword());
        Optional<Customer> customer = customerRepository.findById(loginDTO.getPhoneNo());
        if(customer.isPresent() && customer.get().getPassword().equals(loginDTO.getPassword())){
            isLogged = true;
        }
        return isLogged;
    }

    @Override
    public Mono<CustomerDTO> getCustomerProfile(Long phoneNo) {
        CustomerDTO customerDTO = null;
        logger.info("Profile request for customer {}", phoneNo);
        Optional<Customer> cust = customerRepository.findById(phoneNo);
        if ( cust.isPresent()) {
            customerDTO = CustomerDTO.valueOf(cust.get());
            logger.info("Calling Plan microservice to get the planDTO:");
            customerDTO.setFriendAndFamily(friendFamilyWebClient.getFriends(customerDTO).collectList().block());
            customerDTO.setCurrentPlan(plansWebclient.getPlan(customerDTO).block());
        }
        logger.info("Profile for customer : {}", customerDTO);
        return Mono.just(customerDTO);
    }

}
