package telran.java47.fintech.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TimeHistoryLimitsForIndex {
	LocalDate fromData;
	LocalDate toData;
}
