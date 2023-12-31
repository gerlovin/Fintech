package telran.java47.communication.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParserRequestForTwelveDataDto {
	String[] source;
	LocalDate fromData;
	LocalDate toData;
    String type;
}
