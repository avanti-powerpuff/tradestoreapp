package com.trade.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trade.store.dto.TradeDTO;
import com.trade.store.entity.Trade;
import com.trade.store.exception.TradeException;
import com.trade.store.service.TransmissionService;

@RestController
public class TradeController {

	@Autowired
	private TransmissionService tradeService;

	@PostMapping("/trade/create")
	public ResponseEntity<String> createTrade(@RequestBody TradeDTO trade) throws TradeException {

		boolean created = tradeService.createTrade(trade);

		return new ResponseEntity<String>(created ? HttpStatus.CREATED : HttpStatus.NOT_ACCEPTABLE);

	}

	@GetMapping("/trade/get-all")
	public ResponseEntity<List<Trade>> getAll() {

		return new ResponseEntity<List<Trade>>(tradeService.getAll(), HttpStatus.FOUND);

	}
	
	
	

}
