package telran.java47.communication.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IrrIncomeDto {
	
		LocalDate fromDate;
		LocalDate to;
		ArrayList<String> source;
		String type;
		IncomeIrrDto minIncome;
		IncomeIrrDto maxIncome;

	
}
