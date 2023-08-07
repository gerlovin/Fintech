package telran.java47.communication.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PeriodBeetwinIfoDto {
	LocalDate from;
	LocalDate to;
    String source;
    String type;
    double max;
    double mean;
    double median;
    double min;
    double std;
}

