package telran.java47.fintech.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class NameAmount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
	private Long id;
	double idPackage;
	LocalDateTime timePackage;
	String name;
	Integer amount;
	
	
}
