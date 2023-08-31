package telran.java47.communication.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    Double purchaseAmount;	
    @Setter
    Double saleAmount;
    @Setter
    Double income;
    @Setter
    Double apy;

}
