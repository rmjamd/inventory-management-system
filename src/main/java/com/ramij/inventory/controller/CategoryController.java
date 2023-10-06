package com.ramij.inventory.controller;

import com.ramij.inventory.model.Category;
import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log4j2
public class CategoryController {

	private final CategoryService categoryService;


	@Autowired
	public CategoryController (CategoryService categoryService) {
		this.categoryService = categoryService;
	}


	@PostMapping("/category")
	public ResponseEntity <Category> createCategory (
			@RequestBody
			Category category) {
		log.info("Creating a new category: {}", category);
		Category createdCategory = categoryService.createCategory(category);
		log.info("Created a new category with ID: {}", createdCategory.getCategoryId());
		return new ResponseEntity <>(createdCategory, HttpStatus.CREATED);
	}


	@GetMapping("/categories")
	public ResponseEntity <PageableItems <Category>> getAllCategories (
			@RequestParam(defaultValue = "0")
			int page,
			@RequestParam(defaultValue = "5")
			int size) {
		log.info("Getting all categories with page: {}, size: {}", page, size);
		PageableItems <Category> categories = categoryService.getAllCategories(page, size);
		return new ResponseEntity <>(categories, HttpStatus.OK);
	}


	@GetMapping("/category/{categoryId}")
	public ResponseEntity <Category> getCategoryById (
			@PathVariable
			Long categoryId) {
		log.info("Getting category by ID: {}", categoryId);
		Category category = categoryService.getCategoryById(categoryId);
		return new ResponseEntity <>(category, HttpStatus.OK);
	}


	@PutMapping("/category/{categoryId}")
	public ResponseEntity <Category> updateCategory (
			@PathVariable
			Long categoryId,
			@RequestBody
			Category updatedCategory) {
		log.info("Updating category with ID: {}, updated category: {}", categoryId, updatedCategory);
		Category category = categoryService.updateCategory(categoryId, updatedCategory);
		return new ResponseEntity <>(category, HttpStatus.OK);
	}


	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity <Void> deleteCategory (
			@PathVariable
			Long categoryId) {
		log.info("Deleting category with ID: {}", categoryId);
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity <>(HttpStatus.NO_CONTENT);
	}


	@GetMapping("/category")
	public ResponseEntity <Category> getCategoryByName (
			@RequestParam(name = "name")
			String categoryName) {
		log.info("Getting category by name: {}", categoryName);
		Category category = categoryService.getCategoryByName(categoryName);
		if (category != null) {
			return new ResponseEntity <>(category, HttpStatus.OK);
		} else {
			log.warn("Category with name '{}' not found.", categoryName);
			return new ResponseEntity <>(HttpStatus.NOT_FOUND);
		}
	}
}
