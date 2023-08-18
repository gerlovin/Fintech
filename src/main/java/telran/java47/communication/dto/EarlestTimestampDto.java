package telran.java47.communication.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EarlestTimestampDto {
	LocalDate datetime;
	LocalDate unix_time;
}
