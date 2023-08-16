package telran.java47.communication.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeApyDto {
	LocalDate dateOfPurchase;
    double purchaseAmount;
    LocalDate dateOfSale;
    double saleAmount;
    BigDecimal income;
    BigDecimal apy;

}
