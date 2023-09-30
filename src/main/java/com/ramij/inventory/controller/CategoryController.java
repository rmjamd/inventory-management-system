package com.ramij.inventory.controller;

import com.ramij.inventory.model.Category;
import com.ramij.inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
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
		Category createdCategory = categoryService.createCategory(category);
		return new ResponseEntity <>(createdCategory, HttpStatus.CREATED);
	}


	@GetMapping("/categories")
	public ResponseEntity <Page <Category>> getAllCategories (
			@RequestParam(defaultValue = "0")
			int page,
			@RequestParam(defaultValue = "5")
			int size) {
		Page <Category> categories = categoryService.getAllCategories(page, size);
		return new ResponseEntity <>(categories, HttpStatus.OK);
	}


	@GetMapping("/category{categoryId}")
	public ResponseEntity <Category> getCategoryById (
			@PathVariable
			Long categoryId) {
		Category category = categoryService.getCategoryById(categoryId);
		return new ResponseEntity <>(category, HttpStatus.OK);
	}


	@PutMapping("/category{categoryId}")
	public ResponseEntity <Category> updateCategory (
			@PathVariable
			Long categoryId,
			@RequestBody
			Category updatedCategory) {
		Category category = categoryService.updateCategory(categoryId, updatedCategory);
		return new ResponseEntity <>(category, HttpStatus.OK);
	}


	@DeleteMapping("/category{categoryId}")
	public ResponseEntity <Void> deleteCategory (
			@PathVariable
			Long categoryId) {
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity <>(HttpStatus.NO_CONTENT);
	}


	@GetMapping("/category")
	public ResponseEntity <Category> getCategoryByName (
			@RequestParam(name = "name")
			String categoryName) {
		Category category = categoryService.getCategoryByName(categoryName);
		if (category != null) {
			return new ResponseEntity <>(category, HttpStatus.OK);
		} else {
			return new ResponseEntity <>(HttpStatus.NOT_FOUND);
		}
	}
}
