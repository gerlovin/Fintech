package telran.java47.fintech.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class StockKey implements Serializable{

	private static final long serialVersionUID = 6205196055139659340L;
	
	String name;
	LocalDate dateStock;
}
