package telran.java47.communication.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllApyIncomeDto {
	
	String sourceString;
	LocalDate historyFrom;
	LocalDate historyTo;
	String type;
	LocalDate from;
	LocalDate to;
    double purchaseAmount;	
    double saleAmount;
    BigDecimal income;
    BigDecimal apy;

}
