package com.ramij.inventory.repository;

import com.ramij.inventory.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository <SubCategory, Long> {
	SubCategory findBySubCategoryName (String subCategoryName);
}

