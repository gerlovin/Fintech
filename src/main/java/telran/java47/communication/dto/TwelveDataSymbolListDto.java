package telran.java47.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TwelveDataSymbolListDto {
	
	TwelveSymbolDto[] data;
	String status;
}
