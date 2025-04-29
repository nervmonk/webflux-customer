package com.dwikyryan.customerservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.dwikyryan.customerservice.entity.Customer;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer>{
    
}
