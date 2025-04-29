package com.dwikyryan.customerservice.service;

import org.springframework.stereotype.Service;

import com.dwikyryan.customerservice.dto.CustomerInformation;
import com.dwikyryan.customerservice.entity.Customer;
import com.dwikyryan.customerservice.exceptions.ApplicationExceptions;
import com.dwikyryan.customerservice.mapper.EntityDtoMapper;
import com.dwikyryan.customerservice.repository.CustomerRepository;
import com.dwikyryan.customerservice.repository.PortfolioItemRepository;

import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public CustomerService(CustomerRepository customerRepository, PortfolioItemRepository portfolioItemRepository) {
        this.customerRepository = customerRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    public Mono<CustomerInformation> getCustomerInformation(Integer customerId){
        return this.customerRepository.findById(customerId)
        .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId))
        .flatMap(this::buildCustomerInformation);
    }

    private Mono<CustomerInformation> buildCustomerInformation(Customer customer) {
        return this.portfolioItemRepository.findAllByCustomerId(customer.getId())
                .collectList()
                .map(list -> EntityDtoMapper.toCustomerInformation(customer, list));
    }
}
