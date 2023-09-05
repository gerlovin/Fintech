package telran.java47.fintech.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java47.fintech.model.NameAmount;



public interface PackageRepository extends JpaRepository<NameAmount, Long> {
	
	List<NameAmount> findByTimePackageIsBefore(LocalDateTime dateTime);
	
	@Query("select n from NameAmount n where n.timePackage < ?1")
	List<NameAmount> findByDT(LocalDateTime dt);

}
