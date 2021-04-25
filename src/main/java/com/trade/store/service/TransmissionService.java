package com.trade.store.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.trade.store.dao.TradeDAO;
import com.trade.store.dto.TradeDTO;
import com.trade.store.entity.Trade;
import com.trade.store.exception.TradeException;

/**
 * 
 * @author Avantika Service class to create trade or Transmissions after
 *         validating the requested trade
 *
 */

@Service
public class TransmissionService implements ITransmissionService {

	@Autowired
	private TradeDAO tradeDAO;

	private static final String exMessageVersion = " Trade version is lower than existing. New trade cannot be created";
	private static final String exMessageMaturityDate = " Maturity Date expired. New trade cannot be created";
	private static final int errorCode = 444;

	/**
	 * creates trade based on the Trade details received
	 */
	@Override
	public Boolean createTrade(TradeDTO tradeDTO) throws TradeException {
		boolean tradeCreated = false;
		Optional<Trade> tradeOpt = tradeDAO.findById(tradeDTO.getTradeId());

		boolean tradeExists = tradeOpt.isPresent();

		if (tradeExists) {
			Trade trade = tradeOpt.isPresent() ? tradeOpt.get() : null;

			if (trade != null && trade.getTradeId().equalsIgnoreCase(tradeDTO.getTradeId())) {

				if (trade.getExpiredFlag().equalsIgnoreCase("Y")) {
					throw new TradeException(exMessageMaturityDate, errorCode);
				}

				if (tradeDTO.getVersion().equals(trade.getVersion())) {
					// maturity date check
					trade.setBookId(tradeDTO.getBookId());
					trade.setCounterPartyId(tradeDTO.getCounterPartyId());
					trade.setCreatedDate(LocalDate.now());
					trade.setMaturityDate(tradeDTO.getMaturityDate());
					tradeDAO.save(trade);
					tradeCreated = true;
				} else if (tradeDTO.getVersion() > (trade.getVersion())) {
					// maturity date check
					if (maturityDateCheck(tradeDTO) == false) {
						throw new TradeException(exMessageMaturityDate, errorCode);
					}
					tradeDAO.save(trade);
					tradeCreated = true;
				} else if (tradeDTO.getVersion() < trade.getVersion()) {
					throw new TradeException(exMessageVersion, errorCode);
				}

			} else {
				tradeDAO.save(trade);
				tradeCreated = true;
			}
		} else {

			if (maturityDateCheck(tradeDTO) == false) {
				throw new TradeException(exMessageMaturityDate, errorCode);
			}

			Trade trade = new Trade();
			trade.setTradeId(tradeDTO.getTradeId());
			trade.setBookId(tradeDTO.getBookId());
			trade.setCounterPartyId(tradeDTO.getCounterPartyId());
			trade.setCreatedDate(LocalDate.now());
			trade.setMaturityDate(tradeDTO.getMaturityDate());
			trade.setExpiredFlag("N");
			tradeDAO.save(trade);
		}

		return tradeCreated;
	}

	private boolean maturityDateCheck(TradeDTO dto) throws TradeException {
		if (dto.getMaturityDate().isBefore(LocalDate.now())) {
			return false;
			// throw new TradeException(exMessageMaturityDate,errorCode);
		} else if (dto.getMaturityDate().isAfter(LocalDate.now()) || dto.getMaturityDate().isEqual(LocalDate.now())) {

			return true;
		}

		return false;
	}

	/**
	 * gets all tradeIds
	 */
	@Override
	public List<Trade> getAll() {
		List<Trade> allTrades = tradeDAO.findAll();
		return allTrades;
	}

}
