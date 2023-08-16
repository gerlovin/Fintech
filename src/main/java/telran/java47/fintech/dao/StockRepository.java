package telran.java47.fintech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java47.communication.dto.TimeHistoryLimitsForIndexDto;
import telran.java47.fintech.model.Stock;
import telran.java47.fintech.model.StockKey;

public interface StockRepository extends JpaRepository<Stock, StockKey> {
	
	TimeHistoryLimitsForIndexDto timeLimits(String id);
}
