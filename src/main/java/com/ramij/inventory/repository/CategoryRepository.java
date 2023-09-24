package com.ramij.inventory.repository;

import com.ramij.inventory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, Long> {
	Category findByCategoryName(String categoryName);
}
