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
public class ValueDto implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 2852667557903492122L;
	 LocalDate datetime;
     double close;
     int volume;
     double open;
     double high;
     double low;

}
