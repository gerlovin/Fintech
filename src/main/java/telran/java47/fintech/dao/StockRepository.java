package telran.java47.fintech.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java47.communication.dto.TimeHistoryLimitsForIndexDto;
import telran.java47.fintech.model.Stock;
import telran.java47.fintech.model.StockKey;
import telran.java47.fintech.model.TimeHistoryLimitsForIndex;

import java.util.List;


public interface StockRepository extends JpaRepository<Stock, StockKey> {
	
	@Query("select new telran.java47.fintech.model.TimeHistoryLimitsForIndex( MIN(s.stockKey.dateStock), MAX(s.stockKey.dateStock)) from Stock s where s.stockKey.name LIKE ?1") 
	TimeHistoryLimitsForIndex timeLimits(String name);
	
	int countByStockKeyName(String name);
	

}
