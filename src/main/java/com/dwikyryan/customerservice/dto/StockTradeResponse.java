package com.dwikyryan.customerservice.dto;

import com.dwikyryan.customerservice.domain.Ticker;
import com.dwikyryan.customerservice.domain.TradeAction;

public record StockTradeResponse(Integer customerId, Ticker ticker, Integer price, Integer quantity, TradeAction action, Integer totalPrice, Integer balance) {
    
}
