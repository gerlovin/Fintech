package telran.java47.communication.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValueDto {
	 LocalDate datetime;
     double close;
     long volume;
     double open;
     double high;
     double low;

}
