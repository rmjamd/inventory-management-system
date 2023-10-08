package com.ramij.inventory.repository;

import com.ramij.inventory.model.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignRepository extends JpaRepository<Design, Long> {
    // Custom query method to find a design by its name
    Design findByDesignName(String designName);
}
