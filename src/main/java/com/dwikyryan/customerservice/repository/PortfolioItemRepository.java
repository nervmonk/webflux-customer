package com.dwikyryan.customerservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.dwikyryan.customerservice.entity.PortfolioItem;

import reactor.core.publisher.Flux;

@Repository
public interface PortfolioItemRepository extends ReactiveCrudRepository<PortfolioItem, Integer>{
    Flux<PortfolioItem> findAllByCustomerId(Integer customerId);
}
