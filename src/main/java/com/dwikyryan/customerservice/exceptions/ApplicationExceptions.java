package com.dwikyryan.customerservice.exceptions;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {

    private ApplicationExceptions(){}
    
    public static <T> Mono<T> customerNotFound(Integer customerId){
        return Mono.error(new CustomerNotFoundException(customerId));
    }

    public static <T> Mono<T> insufficientBalance(Integer customerId){
        return Mono.error(new InsufficientBalanceException(customerId));
    }

    public static <T> Mono<T> insufficientShares(Integer customerId){
        return Mono.error(new InsufficientShareException(customerId));
    }
}
