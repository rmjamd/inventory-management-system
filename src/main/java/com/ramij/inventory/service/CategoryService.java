package com.ramij.inventory.service;

import com.ramij.inventory.exceptions.ResourceException;
import com.ramij.inventory.exceptions.ResourceExistsException;
import com.ramij.inventory.model.Category;
import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class CategoryService {

	private final CategoryRepository categoryRepository;


	@Autowired
	public CategoryService (CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}


	@Transactional
	public Category createCategory (Category category) {
		log.info("Creating a new category: {}", category);

		try {
			if (categoryRepository.findByCategoryName(category.getCategoryName()) != null) {
				throw new ResourceExistsException("Category name must be unique");
			}
			return categoryRepository.save(category);
		} catch (Exception ex) {
			log.error("Error occurred while creating a category: {}", ex.getMessage());
			throw new ResourceException("Error occurred to create a category");
		}
	}

	@Transactional
	public PageableItems <Category> getAllCategories (int pageNo, int size) {
		log.info("Getting all categories with page: {}, size: {}", pageNo, size);

		try {
			Pageable        pageable = PageRequest.of(pageNo, size);
			Page <Category> pages    = categoryRepository.findAll(pageable);
			return new PageableItems <>(pages.getContent(), pages.getTotalPages());
		} catch (Exception ex) {
			log.error("Error occurred while getting all categories: {}", ex.getMessage());
			throw new ResourceException("Error occurred to get categories");
		}
	}

	@Transactional
	public Category getCategoryById (Long categoryId) {
		log.info("Getting category by ID: {}", categoryId);

		try {
			return categoryRepository.findById(categoryId)
									 .orElseThrow(() -> new ResourceException(String.format("Category with ID %d not found", categoryId)));
		} catch (Exception ex) {
			log.error("Error occurred while getting a category by ID: {}", ex.getMessage());
			throw new ResourceException("Error occurred to get a category");
		}
	}

	@Transactional
	public Category updateCategory (Long categoryId, Category updatedCategory) {
		log.info("Updating category with ID: {}, updated category: {}", categoryId, updatedCategory);

		try {
			if (!categoryRepository.existsById(categoryId)) {
				throw new ResourceException(String.format("Category with ID %d not found", categoryId));
			}

			// Check if the updated category name is unique
			Category existingCategoryWithSameName = categoryRepository.findByCategoryName(updatedCategory.getCategoryName());
			if (existingCategoryWithSameName != null && !existingCategoryWithSameName.getCategoryId().equals(categoryId)) {
				throw new ResourceExistsException("Category name must be unique");
			}

			updatedCategory.setCategoryId(categoryId);
			return categoryRepository.save(updatedCategory);
		} catch (Exception ex) {
			log.error("Error occurred while updating a category: {}", ex.getMessage());
			throw new ResourceException("Error occurred to update a category");
		}
	}

	@Transactional
	public void deleteCategory (Long categoryId) {
		log.info("Deleting category with ID: {}", categoryId);

		try {
			if (!categoryRepository.existsById(categoryId)) {
				throw new ResourceException(String.format("Category with ID %d not found", categoryId));
			}
			categoryRepository.deleteById(categoryId);
		} catch (Exception ex) {
			log.error("Error occurred while deleting a category: {}", ex.getMessage());
			throw new ResourceException("Error occurred to delete a category");
		}
	}

	@Transactional
	public Category getCategoryByName (String categoryName) {
		log.info("Getting category by name: {}", categoryName);

		try {
			Category category = categoryRepository.findByCategoryName(categoryName);
			if (category == null) {
				throw new ResourceException("Category with name " + categoryName + " not found");
			}
			return category;
		} catch (Exception ex) {
			log.error("Error occurred while getting a category by name: {}", ex.getMessage());
			throw new ResourceException("Error occurred to get a category by name");
		}
	}
}
