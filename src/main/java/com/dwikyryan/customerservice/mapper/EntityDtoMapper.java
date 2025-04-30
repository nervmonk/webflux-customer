package com.dwikyryan.customerservice.mapper;

import java.util.List;

import com.dwikyryan.customerservice.domain.Ticker;
import com.dwikyryan.customerservice.dto.CustomerInformation;
import com.dwikyryan.customerservice.dto.Holding;
import com.dwikyryan.customerservice.dto.StockTradeRequest;
import com.dwikyryan.customerservice.dto.StockTradeResponse;
import com.dwikyryan.customerservice.entity.Customer;
import com.dwikyryan.customerservice.entity.PortfolioItem;

public class EntityDtoMapper {

    private EntityDtoMapper(){}
    
    public static CustomerInformation toCustomerInformation(Customer customer, List<PortfolioItem> items){
        var holdings = items.stream()
        .map(i -> new Holding(i.getTicker(), i.getQuantity()))
        .toList();

        return new CustomerInformation(customer.getId(), customer.getName(), customer.getBalance(), holdings);
    }

    public static PortfolioItem toPortfolioItem(Integer customerId, Ticker ticker){
        var portfolioItem = new PortfolioItem();
        portfolioItem.setCustomerId(customerId);
        portfolioItem.setTicker(ticker);
        portfolioItem.setQuantity(0);
        return portfolioItem;
    }

    public static StockTradeResponse toStockTradeResponse(StockTradeRequest request, Integer customerId, Integer balance){
        return new StockTradeResponse(customerId, request.ticker(), request.price(), request.quantity(), request.action(), request.totalPrice(), balance);
    }
}
