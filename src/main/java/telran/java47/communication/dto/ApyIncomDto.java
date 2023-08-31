package telran.java47.communication.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApyIncomDto {
	
	LocalDate fromDate;
	LocalDate to;
	String[] source;
	String type;
	IncomeApyDto minIncome;
	IncomeApyDto maxIncome;

}
