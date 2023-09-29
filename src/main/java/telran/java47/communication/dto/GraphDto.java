package telran.java47.communication.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class GraphDto {
	String name;
	LocalDate dateStock;
	Double openV;
	Double highV;
	Double lowV;
	Double closeV;
	Double adjCloseV;
	Long volume;
	Boolean workDayOrNot;
}
