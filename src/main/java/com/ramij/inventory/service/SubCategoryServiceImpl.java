package com.ramij.inventory.service;

import com.ramij.inventory.exceptions.ResourceExistsException;
import com.ramij.inventory.model.Category;
import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.model.SubCategory;
import com.ramij.inventory.repository.SubCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {
	private final SubCategoryRepository subCategoryRepository;
	private final CategoryService categoryService;


	@Autowired
	public SubCategoryServiceImpl (SubCategoryRepository subCategoryRepository, CategoryService categoryService) {
		this.subCategoryRepository = subCategoryRepository;
		this.categoryService       = categoryService;
	}


	@Override
	@Transactional
	public SubCategory createSubCategory (SubCategory subCategory) {
		if (subCategory.getCategory() == null || subCategory.getCategory().getCategoryId() == null) {
			throw new IllegalArgumentException("Category is mandatory for SubCategory");
		}
		Category category=categoryService.getCategoryById(subCategory.getCategory().getCategoryId());
		subCategory.setCategory(category);
		SubCategory existingSubCategory = subCategoryRepository.findBySubCategoryName(subCategory.getSubCategoryName());

		if (existingSubCategory != null) {
			throw new ResourceExistsException("SubCategory with the same name already exists.");
		}
		return subCategoryRepository.save(subCategory);
	}


	@Override
	@Transactional(readOnly = true)
	public PageableItems <SubCategory> getAllSubCategory (int pageNo, int size) {
		Page <SubCategory> subCategoriesPage = subCategoryRepository.findAll(PageRequest.of(pageNo, size));
		List <SubCategory> subCategories     = subCategoriesPage.getContent();
		int                totalPages        = subCategoriesPage.getTotalPages();
		return new PageableItems <>(subCategories, totalPages);
	}


	@Override
	@Transactional
	public SubCategory updateSubCategory (Long subCategoryId, SubCategory updatedSubCategory) {
		// Check if the subCategory with the given ID exists
		SubCategory existingSubCategory = subCategoryRepository.findById(subCategoryId)
															   .orElseThrow(() -> new EntityNotFoundException("SubCategory with ID " + subCategoryId + " not found"));

		// Update the existing subCategory with the new data
		existingSubCategory.setSubCategoryName(updatedSubCategory.getSubCategoryName());
		// Add more fields to update as needed

		return subCategoryRepository.save(existingSubCategory);
	}


	@Override
	@Transactional(readOnly = true)
	public SubCategory getSubCategoryById (Long subCategoryId) {
		return subCategoryRepository.findById(subCategoryId)
									.orElseThrow(() -> new EntityNotFoundException("SubCategory with ID " + subCategoryId + " not found"));
	}


	@Override
	@Transactional(readOnly = true)
	public SubCategory getSubCategoryByName (String subCategoryName) {
		SubCategory subCategory = subCategoryRepository.findBySubCategoryName(subCategoryName);
		if (subCategory == null) {
			throw new EntityNotFoundException("SubCategory with name " + subCategoryName + " not found");
		}
		return subCategory;
	}


	@Override
	@Transactional
	public void deleteSubCategory (Long subCategoryId) {
		// Check if the subCategory with the given ID exists
		SubCategory existingSubCategory = subCategoryRepository.findById(subCategoryId)
															   .orElseThrow(() -> new EntityNotFoundException("SubCategory with ID " + subCategoryId + " not found"));

		subCategoryRepository.delete(existingSubCategory);
	}

}

