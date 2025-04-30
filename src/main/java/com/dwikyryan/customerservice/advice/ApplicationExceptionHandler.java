package com.dwikyryan.customerservice.advice;

import java.net.URI;
import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dwikyryan.customerservice.exceptions.CustomerNotFoundException;
import com.dwikyryan.customerservice.exceptions.InsufficientBalanceException;
import com.dwikyryan.customerservice.exceptions.InsufficientShareException;

@ControllerAdvice
public class ApplicationExceptionHandler {
    
    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleException(CustomerNotFoundException ex){
        return build(HttpStatus.NOT_FOUND, ex, p -> {
            p.setType(URI.create("http://example.com/problems/customer-not-found"));
            p.setTitle("Customer Not Found");
        });
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ProblemDetail handleException(InsufficientBalanceException ex){
        return build(HttpStatus.NOT_FOUND, ex, p -> {
            p.setType(URI.create("http://example.com/problems/insufficient-balance"));
            p.setTitle("Insufficient balance");
        });
    }

    @ExceptionHandler(InsufficientShareException.class)
    public ProblemDetail handleException(InsufficientShareException ex){
        return build(HttpStatus.NOT_FOUND, ex, p -> {
            p.setType(URI.create("http://example.com/problems/insufficient-shares"));
            p.setTitle("Insufficient shares");
        });
    }

    private ProblemDetail build(HttpStatus status, Exception ex, Consumer<ProblemDetail> consumer){
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        consumer.accept(problem);
        return problem;
    }
}
