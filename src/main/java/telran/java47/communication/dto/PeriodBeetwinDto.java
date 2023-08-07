package telran.java47.communication.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PeriodBeetwinDto {
	String[] indexs;
    TimeType type;
    int quantity;
	LocalDate from;
    LocalDate to;
}
