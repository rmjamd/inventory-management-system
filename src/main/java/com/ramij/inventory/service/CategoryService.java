package com.ramij.inventory.service;

import com.ramij.inventory.exceptions.ResourceExistsExceptions;
import com.ramij.inventory.model.Category;
import com.ramij.inventory.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Category createCategory(Category category) {
		if (categoryRepository.findByCategoryName(category.getCategoryName()) != null) {
			throw new ResourceExistsExceptions("Category name must be unique");
		}
		return categoryRepository.save(category);
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
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
			throw new ResourceExistsExceptions("Category name must be unique");
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
