package telran.java47.communication.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ValueCloseBeetwinDto {
	LocalDate from;
	LocalDate to;
    String source;
    String type;
    LocalDate minDate;
    LocalDate maxDate;
    double startClose;
    @Setter
    double endClose;
    @Setter
    double valueClose;
    ArrayList<Double> listClose;
  
}
