package com.dwikyryan.customerservice;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;

import com.dwikyryan.customerservice.domain.Ticker;
import com.dwikyryan.customerservice.domain.TradeAction;
import com.dwikyryan.customerservice.dto.StockTradeRequest;

@SpringBootTest
@AutoConfigureWebTestClient
class CustomerserviceApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(CustomerserviceApplicationTests.class);

	@Autowired
	private WebTestClient client;

	@Test
	void customerInformation() {
		getCustomer(1, HttpStatus.OK)
				.jsonPath("$.name").isEqualTo("Sam")
				.jsonPath("$.balance").isEqualTo(10000)
				.jsonPath("$.holdings").isEmpty();

	}

	@Test
	void buyAndSell() {
		// buy
		var buyRequest1 = new StockTradeRequest(Ticker.GOOGLE, 100, 5, TradeAction.BUY);
		trade(2, buyRequest1, HttpStatus.OK)
				.jsonPath("$.balance").isEqualTo(9500)
				.jsonPath("$.totalPrice").isEqualTo(500);

		var buyRequest2 = new StockTradeRequest(Ticker.GOOGLE, 100, 10, TradeAction.BUY);
		trade(2, buyRequest2, HttpStatus.OK)
				.jsonPath("$.balance").isEqualTo(8500)
				.jsonPath("$.totalPrice").isEqualTo(1000);

		getCustomer(2, HttpStatus.OK)
				.jsonPath("$.holdings").isNotEmpty()
				.jsonPath("$.holdings.length()").isEqualTo(1)
				.jsonPath("$.holdings[0].ticker").isEqualTo(Ticker.GOOGLE)
				.jsonPath("$.holdings[0].quantity").isEqualTo(15);

		var sellRequest1 = new StockTradeRequest(Ticker.GOOGLE, 110, 5, TradeAction.SELL);
		trade(2, sellRequest1, HttpStatus.OK)
				.jsonPath("$.balance").isEqualTo(9050)
				.jsonPath("$.totalPrice").isEqualTo(550);

		var sellRequest2 = new StockTradeRequest(Ticker.GOOGLE, 110, 10, TradeAction.SELL);
		trade(2, sellRequest2, HttpStatus.OK)
				.jsonPath("$.balance").isEqualTo(10150)
				.jsonPath("$.totalPrice").isEqualTo(1100);

		getCustomer(2, HttpStatus.OK)
				.jsonPath("$.holdings").isNotEmpty()
				.jsonPath("$.holdings.length()").isEqualTo(1)
				.jsonPath("$.holdings[0].ticker").isEqualTo(Ticker.GOOGLE)
				.jsonPath("$.holdings[0].quantity").isEqualTo(0);
	}

	@Test
	void customerNotFound() {
		getCustomer(10, HttpStatus.NOT_FOUND)
				.jsonPath("$.detail").isEqualTo("Customer [id=10] is not found");

		var sellRequest = new StockTradeRequest(Ticker.GOOGLE, 110, 5, TradeAction.SELL);
		trade(10, sellRequest, HttpStatus.NOT_FOUND)
				.jsonPath("$.detail").isEqualTo("Customer [id=10] is not found");
	}

	@Test
	void insufficientBalance() {
		var buyRequest = new StockTradeRequest(Ticker.GOOGLE, 100, 101, TradeAction.BUY);
		trade(3, buyRequest, HttpStatus.BAD_REQUEST)
				.jsonPath("$.detail")
				.isEqualTo("Customer [id=3] does not have enough funds to complete the transaction");
	}

	@Test
	void insufficientShares() {
		var sellRequest = new StockTradeRequest(Ticker.GOOGLE, 100, 1, TradeAction.SELL);
		trade(3, sellRequest, HttpStatus.BAD_REQUEST)
				.jsonPath("$.detail")
				.isEqualTo("Customer [id=3] does not have enough shares to complete the transaction");
	}

	private BodyContentSpec getCustomer(Integer customerId, HttpStatus expectedStatus) {
		return this.client.get()
				.uri("/customers/{customerId}", customerId)
				.exchange()
				.expectStatus().isEqualTo(expectedStatus)
				.expectBody()
				.consumeWith(e -> log.info("{}", new String(Objects.requireNonNull(e.getResponseBody()))));
	}

	private BodyContentSpec trade(Integer customerId, StockTradeRequest request, HttpStatus expectedStatus) {
		return this.client.post()
				.uri("/customers/{customerId}/trade", customerId)
				.bodyValue(request)
				.exchange()
				.expectStatus().isEqualTo(expectedStatus)
				.expectBody()
				.consumeWith(e -> log.info("{}", new String(Objects.requireNonNull(e.getResponseBody()))));
	}

}
