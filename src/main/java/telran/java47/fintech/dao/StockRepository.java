package telran.java47.fintech.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import telran.java47.fintech.model.Stock;
import telran.java47.fintech.model.StockKey;
import telran.java47.fintech.model.TimeHistoryLimitsForIndex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



public interface StockRepository extends JpaRepository<Stock, StockKey> {
	
	@Query("select new telran.java47.fintech.model.TimeHistoryLimitsForIndex( MIN(s.stockKey.dateStock), MAX(s.stockKey.dateStock)) from Stock s where s.stockKey.name LIKE ?1") 
	TimeHistoryLimitsForIndex timeLimits(String name);
	

	//test
	@Procedure("PeriodForOneIndexv3")
	String testProcedureCall(String name, String typePeriod, int lenghtPeriod, LocalDate dateFrom, LocalDate dateTo);
	
	@Procedure("PeriodForIndexInInterval")
	String periodInfo(String name, String typePeriod, int lenghtPeriod, LocalDate dateFrom, LocalDate dateTo);
	
	@Query("select s from Stock s where s.stockKey.name LIKE ?1 AND  s.stockKey.dateStock BETWEEN ?2 AND ?3")
	List<Stock> findByStockKeyNameIgnoreCaseStockKeyDateStockBetween(String name, LocalDate dateFrom, LocalDate dateTo);
	
	@Procedure("CorrelationCalc")
	String correlationCalc(String name1, String name2, LocalDate dateFrom, LocalDate dateTo);
	
	@Procedure("CalcSumPackage")
	String calcSumPackage(Double idPackage, LocalDateTime timePackage, String typePeriod, int lenghtPeriod, LocalDate dateFrom, LocalDate dateTo);

	@Query("Select DISTINCT s.stockKey.name from Stock s")
	List<String> findDistinctByStockKeyName();
}
