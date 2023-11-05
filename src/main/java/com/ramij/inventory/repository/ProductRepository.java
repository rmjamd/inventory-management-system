package com.ramij.inventory.repository;

import com.ramij.inventory.model.Color;
import com.ramij.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Add custom query methods if needed

	@Query("SELECT p FROM Product p WHERE p.designName=?1 AND p.size.sizeName = ?2 AND p.color = ?3")
	Optional <Product> findByDesignIdAndProductSize (String designName, Product.Size.SizeName sizeName, Color color);
}
