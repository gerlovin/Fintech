package telran.java47.communication.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoDto {
	
	String symbol;
	String fullSymbolName;
	String exchangeString;
	LocalDate startDate;
}
