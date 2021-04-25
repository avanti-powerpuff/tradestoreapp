package com.trade.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trade.store.entity.Trade;



public interface TradeDAO  extends JpaRepository<Trade,String> {
	
	
	
	
}
