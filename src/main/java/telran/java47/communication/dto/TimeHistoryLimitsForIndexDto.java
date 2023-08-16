package telran.java47.communication.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeHistoryLimitsForIndexDto {
	String source;
	LocalDate fromData;
	LocalDate toData;
}
