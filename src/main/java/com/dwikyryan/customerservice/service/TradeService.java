package com.dwikyryan.customerservice.service;

import org.springframework.stereotype.Service;

import com.dwikyryan.customerservice.dto.StockTradeRequest;
import com.dwikyryan.customerservice.dto.StockTradeResponse;
import com.dwikyryan.customerservice.repository.CustomerRepository;
import com.dwikyryan.customerservice.repository.PortfolioItemRepository;

import reactor.core.publisher.Mono;

@Service
public class TradeService {
    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public TradeService(CustomerRepository customerRepository, PortfolioItemRepository portfolioItemRepository){
        this.customerRepository = customerRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    // public Mono<StockTradeResponse> trade(Integer customerId, StockTradeRequest request){

    // }
}
