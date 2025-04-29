package com.dwikyryan.customerservice.dto;

import com.dwikyryan.customerservice.domain.Ticker;

public record Holding(Ticker ticker, Integer quantity) {
    
}
