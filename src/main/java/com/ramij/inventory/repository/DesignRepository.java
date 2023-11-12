package com.ramij.inventory.repository;

import com.ramij.inventory.model.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignRepository extends JpaRepository<Design, Long> {
    // Custom query method to find a design by its name
    Design findByDesignName(String designName);
	@Query("SELECT d.designName FROM Design d")
	List <String> findAllDesignNames ();

}
