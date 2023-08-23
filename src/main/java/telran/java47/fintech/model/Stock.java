package telran.java47.fintech.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "stockKey")
@Builder
@Entity
@Table(name = "Stocks")
public class Stock {
	@EmbeddedId
	StockKey stockKey;
	Double openV;
	Double highV;
	Double lowV;
	Double closeV;
	Double adjCloseV;
	Integer volume;
	Boolean workDayOrNot;

}
