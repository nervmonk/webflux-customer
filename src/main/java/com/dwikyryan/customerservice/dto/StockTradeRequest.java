package com.dwikyryan.customerservice.dto;

import com.dwikyryan.customerservice.domain.Ticker;
import com.dwikyryan.customerservice.domain.TradeAction;

public record StockTradeRequest(Ticker ticker, Integer price, Integer quantity, TradeAction action) {
    public Integer totalPrice(){
        return price * quantity;
    }
}
