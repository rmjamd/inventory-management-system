package com.ramij.inventory.service;

import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.model.SubCategory;

import java.util.List;


public interface SubCategoryService {
	SubCategory createSubCategory (SubCategory subCategory);

	PageableItems <SubCategory> getAllSubCategory (int pageNo, int size);

	SubCategory getSubCategoryById (Long subCategoryId);

	SubCategory updateSubCategory (Long subCategoryId, SubCategory updatedSubCategory);

	void deleteSubCategory (Long subCategoryId);

	SubCategory getSubCategoryByName (String subCategoryName);

	List<String> getAllSubcategoryNames ();
}

