package telran.java47.communication.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ValueCloseBeetwinDto {
	LocalDate from;
	LocalDate to;
    String source;
    String type;
    LocalDate minDate;
    LocalDate maxDate;
    double startClose;
    double endClose;
    double valueClose;
    ArrayList<Double> listClose;
  
}
