package com.ramij.inventory.service;

import com.ramij.inventory.exceptions.ResourceExistsException;
import com.ramij.inventory.model.Category;
import com.ramij.inventory.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Category createCategory(Category category) {
		if (categoryRepository.findByCategoryName(category.getCategoryName()) != null) {
			throw new ResourceExistsException("Category name must be unique");
		}
		return categoryRepository.save(category);
	}


	public Page <Category> getAllCategories (int pageNo, int size) {
		Pageable pageable = PageRequest.of(pageNo, size);
		return categoryRepository.findAll(pageable);
	}

	public Category getCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId)
								 .orElseThrow(() -> new EntityNotFoundException("Category with ID %d not found".formatted(categoryId)));
	}

	public Category updateCategory(Long categoryId, Category updatedCategory) {
		if (!categoryRepository.existsById(categoryId)) {
			throw new EntityNotFoundException("Category with ID " + categoryId + " not found");
		}
		// Check if the updated category name is unique
		Category existingCategoryWithSameName = categoryRepository.findByCategoryName(updatedCategory.getCategoryName());
		if (existingCategoryWithSameName != null && !existingCategoryWithSameName.getCategoryId().equals(categoryId)) {
			throw new ResourceExistsException("Category name must be unique");
		}
		updatedCategory.setCategoryId(categoryId);
		return categoryRepository.save(updatedCategory);
	}

	public void deleteCategory(Long categoryId) {
		if (!categoryRepository.existsById(categoryId)) {
			throw new EntityNotFoundException("Category with ID " + categoryId + " not found");
		}
		categoryRepository.deleteById(categoryId);
	}
	public Category getCategoryByName(String categoryName) {
		Category category = categoryRepository.findByCategoryName(categoryName);
		if (category == null) {
			throw new EntityNotFoundException("Category with name " + categoryName + " not found");
		}
		return category;
	}
}
