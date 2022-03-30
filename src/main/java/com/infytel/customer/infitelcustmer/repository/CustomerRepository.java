package com.infytel.customer.infitelcustmer.repository;

import com.infytel.customer.infitelcustmer.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer,Long> {
}
