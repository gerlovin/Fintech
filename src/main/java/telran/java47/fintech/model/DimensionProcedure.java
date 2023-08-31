package telran.java47.fintech.model;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
public class DimensionProcedure {
	@Id
	LocalDate dateOfPurchase;
    double purchaseAmount;
    LocalDate dateOfSale;
    double saleAmount;
    double income;
    double dimension;
}
