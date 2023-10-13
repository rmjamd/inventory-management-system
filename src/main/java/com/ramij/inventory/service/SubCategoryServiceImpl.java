package com.ramij.inventory.service;

import com.ramij.inventory.exceptions.ResourceException;
import com.ramij.inventory.model.Category;
import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.model.SubCategory;
import com.ramij.inventory.repository.SubCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class SubCategoryServiceImpl implements SubCategoryService {
	private final SubCategoryRepository subCategoryRepository;
	private final CategoryService       categoryService;


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
		try {
			Category category = categoryService.getCategoryById(subCategory.getCategory().getCategoryId());
			subCategory.setCategory(category);
			return subCategoryRepository.save(subCategory);
		} catch (Exception e) {
			log.error("Failed to create subCategory: ", e);
			throw new ResourceException("Failed to create subCategory");
		}
	}


	@Override
	@Transactional(readOnly = true)
	public PageableItems <SubCategory> getAllSubCategory (int pageNo, int size) {
		try {
			Page <SubCategory> subCategoriesPage = subCategoryRepository.findAll(PageRequest.of(pageNo, size));
			List <SubCategory> subCategories     = subCategoriesPage.getContent();
			int                totalPages        = subCategoriesPage.getTotalPages();
			return new PageableItems <>(subCategories, totalPages);
		} catch (Exception e) {
			log.error("Failed to get all subCategory: ", e);
			throw new ResourceException("Failed to get all subCategory");
		}
	}


	@Override
	@Transactional
	public SubCategory updateSubCategory (Long subCategoryId, SubCategory updatedSubCategory) {
		// Check if the subCategory with the given ID exists
		try {
			SubCategory existingSubCategory = subCategoryRepository.findById(subCategoryId)
																   .orElseThrow(() -> new EntityNotFoundException("SubCategory with ID " + subCategoryId + " not found"));

			// Update the existing subCategory with the new data
			existingSubCategory.setSubCategoryName(updatedSubCategory.getSubCategoryName());
			// Add more fields to update as needed

			return subCategoryRepository.save(existingSubCategory);
		} catch (Exception e) {
			log.error("Failed to Update subCategory: ", e);
			throw new ResourceException("Failed to Update subCategory");
		}
	}


	@Override
	@Transactional(readOnly = true)
	public SubCategory getSubCategoryById (Long subCategoryId) {
		try {
			return subCategoryRepository.findById(subCategoryId)
										.orElseThrow(() -> new EntityNotFoundException("SubCategory with ID " + subCategoryId + " not found"));
		} catch (Exception e) {
			log.error("Failed to fetch a subCategory: ", e);
			throw new ResourceException("Failed to fetch a subCategory");
		}
	}


	@Override
	@Transactional(readOnly = true)
	public SubCategory getSubCategoryByName (String subCategoryName) {
		SubCategory subCategory = null;
		try {
			subCategory = subCategoryRepository.findBySubCategoryName(subCategoryName);
		} catch (Exception e) {
			log.error("Failed to get subCategory By Name: " + subCategoryName, e);
			throw new ResourceException("Failed to get subCategory By Name: " + subCategoryName);
		}
		if (subCategory == null) {
			throw new EntityNotFoundException("SubCategory with name " + subCategoryName + " not found");
		}
		return subCategory;
	}


	@Override
	@Transactional
	public void deleteSubCategory (Long subCategoryId) {
		try {
			// Check if the subCategory with the given ID exists
			SubCategory existingSubCategory = subCategoryRepository.findById(subCategoryId)
																   .orElseThrow(() -> new EntityNotFoundException("SubCategory with ID " + subCategoryId + " not found"));
			subCategoryRepository.delete(existingSubCategory);
		} catch (Exception e) {
			log.error("Failed to delete subCategory", e);
			throw new ResourceException("Failed to delete subCategory");
		}
	}

}

