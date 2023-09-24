package telran.java47.fintech.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import telran.java47.fintech.model.DimensionProcedure;

public interface DimensionRepository extends JpaRepository<DimensionProcedure, LocalDate> {
	
	@Transactional
	@Procedure("IncomeWithAPY")
	ArrayList<DimensionProcedure> incomeWithAPI(String name, String typePeriod, int lenghtPeriod, Double lenghtPeriodYears, LocalDate dateFrom, LocalDate dateTo);

}
