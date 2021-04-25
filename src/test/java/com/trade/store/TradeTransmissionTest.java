package com.trade.store;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.trade.store.controller.TradeController;

import com.trade.store.dto.TradeDTO;
import com.trade.store.entity.Trade;
import com.trade.store.exception.TradeException;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class TradeTransmissionTest {

	@Autowired
	private TradeController tradeController;

	@Test

	void createTrade() throws TradeException {
		TradeDTO tradeDTO1 = dtoSetupForId("T9");
		tradeController.createTrade(tradeDTO1);

		Assertions.assertTrue(tradeController.getAll().getStatusCode().equals(HttpStatus.FOUND));

		ResponseEntity<List<Trade>> responseEntity = tradeController.getAll();

		Assertions.assertEquals(responseEntity.getBody().size(), 1);

	}

	@Test
	void createTradeExceptionCheck() throws TradeException {

		tradeController.createTrade(dtoSetupForVersion(3));

		Assertions.assertThrows(TradeException.class, () -> {

			tradeController.createTrade(dtoSetupForVersion(1));
		});

	}

	private TradeDTO dtoSetupForId(String id) {

		TradeDTO trade = new TradeDTO();
		trade.setBookId("B1");
		trade.setTradeId(id);
		trade.setVersion(1);
		trade.setCounterPartyId("CP2");
		trade.setMaturityDate(LocalDate.now());
		trade.setCreatedDate(LocalDate.now());
		trade.setExpiredFlag("N");

		return trade;

	}

	private TradeDTO dtoSetupForVersion(int version) {

		TradeDTO trade = new TradeDTO();
		trade.setBookId("B1");
		trade.setTradeId("T1");
		trade.setVersion(version);
		trade.setCounterPartyId("CP2");
		trade.setMaturityDate(LocalDate.now());
		trade.setCreatedDate(LocalDate.now());
		trade.setExpiredFlag("N");

		return trade;

	}

}
