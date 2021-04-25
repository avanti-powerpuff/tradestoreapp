package com.trade.store.service;

import java.util.List;

import com.trade.store.dto.TradeDTO;
import com.trade.store.entity.Trade;
import com.trade.store.exception.TradeException;

public interface ITransmissionService {
	
	public Boolean createTrade(TradeDTO tradeDTO) throws TradeException;
	
	public List<Trade> getAll();
	

}
