package telran.java47.communication.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalcSumPackageDto {
	ArrayList<String> indexes;
	ArrayList<Integer> amount;
    LocalDate from;
    LocalDate to;
    String type;
    int quantity;
}
