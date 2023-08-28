package telran.java47.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetaDto {
	String symbol;
	String interval;
	String currency;
	String exchange_timzone;
	String exchange;
	String mic_code;
	String typeString;
	
	
}
