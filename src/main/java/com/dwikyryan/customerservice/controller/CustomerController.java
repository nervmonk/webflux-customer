package com.dwikyryan.customerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dwikyryan.customerservice.dto.CustomerInformation;
import com.dwikyryan.customerservice.dto.StockTradeRequest;
import com.dwikyryan.customerservice.dto.StockTradeResponse;
import com.dwikyryan.customerservice.service.CustomerService;
import com.dwikyryan.customerservice.service.TradeService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private final CustomerService customerService;
    private final TradeService tradeService;

    public CustomerController(CustomerService customerService, TradeService tradeService){
        this.customerService = customerService;
        this.tradeService = tradeService;
    }

    @GetMapping("/{customerId}")
    public Mono<CustomerInformation> getCustomerInformation(@PathVariable Integer customerId){
        return this.customerService.getCustomerInformation(customerId);
    }

    @PostMapping("/{customerId}/trade")
    public Mono<StockTradeResponse> trade(@PathVariable Integer customerId, @RequestBody Mono<StockTradeRequest> mono){
        return mono.flatMap(req -> this.tradeService.trade(customerId, req));
    }
}
