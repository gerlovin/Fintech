package telran.java47.fintech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java47.fintech.model.NameAmount;

public interface PackageRepository extends JpaRepository<NameAmount, Long> {

}
