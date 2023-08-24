package telran.java47.fintech.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;


import telran.java47.fintech.model.PeriodBeetwinInfo;
import telran.java47.fintech.model.Stock;
import telran.java47.fintech.model.StockKey;
import telran.java47.fintech.model.TimeHistoryLimitsForIndex;

import java.time.LocalDate;



public interface StockRepository extends JpaRepository<Stock, StockKey> {
	
	@Query("select new telran.java47.fintech.model.TimeHistoryLimitsForIndex( MIN(s.stockKey.dateStock), MAX(s.stockKey.dateStock)) from Stock s where s.stockKey.name LIKE ?1") 
	TimeHistoryLimitsForIndex timeLimits(String name);
	
	
	//test
	int countByStockKeyName(String name);
	
	@Procedure("TestProcedure")
	int testProcedureCall(int arg);
	
	@Procedure("java47.PeriodForOneIndexv3")
	PeriodBeetwinInfo periodInfo(String name, String typePeriod, int lenghtPeriod, LocalDate dateFrom, LocalDate dateTo);
	

}
