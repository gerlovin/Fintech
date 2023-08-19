package telran.java47.communication.dto;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TwelveSymbolDto {
	
	String symbol;
	String name;
	String currency;
	String exchange;
	String mic_code;
	String country;
	String type;
}
