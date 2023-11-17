package com.ramij.inventory.controller;

import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.model.SubCategory;
import com.ramij.inventory.service.SubCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubCategoryController {

	private final SubCategoryService subCategoryService;
	private final Logger logger = LoggerFactory.getLogger(SubCategoryController.class);

	@Autowired
	public SubCategoryController(SubCategoryService subCategoryService) {
		this.subCategoryService = subCategoryService;
	}

	@PostMapping("/subcategory")
	public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategory subCategory) {
		logger.info("Creating subcategory: {}", subCategory);
		SubCategory createdSubCategory = subCategoryService.createSubCategory(subCategory);
		logger.info("Subcategory created: {}", createdSubCategory);
		return new ResponseEntity<>(createdSubCategory, HttpStatus.CREATED);
	}

	@GetMapping("/subcategories")
	public ResponseEntity<PageableItems<SubCategory>> getAllSubcategory(
			@RequestParam(name = "page", defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "5") int size) {
		logger.info("Getting all subcategories");
		PageableItems<SubCategory> pageableItems = subCategoryService.getAllSubCategory(pageNo, size);
		logger.info("Fetched {} subcategories", pageableItems.getContents().size());
		return new ResponseEntity<>(pageableItems, HttpStatus.OK);
	}

	@GetMapping("/subcategory/{subCategoryId}")
	public ResponseEntity<SubCategory> getSubCategory(@PathVariable Long subCategoryId) {
		logger.info("Getting subcategory by ID: {}", subCategoryId);
		SubCategory subCategory = subCategoryService.getSubCategoryById(subCategoryId);
		logger.info("Fetched subcategory: {}", subCategory);
		return new ResponseEntity<>(subCategory, HttpStatus.OK);
	}

	@PutMapping("/subcategory/{subCategoryId}")
	public ResponseEntity<SubCategory> updateSubCategory(
			@PathVariable Long subCategoryId,
			@RequestBody SubCategory updatedSubCategory) {
		logger.info("Updating subcategory with ID: {}", subCategoryId);
		SubCategory subCategory = subCategoryService.updateSubCategory(subCategoryId, updatedSubCategory);
		logger.info("Updated subcategory: {}", subCategory);
		return new ResponseEntity<>(subCategory, HttpStatus.OK);
	}

	@DeleteMapping("/subcategory/{subCategoryId}")
	public ResponseEntity<Void> deleteSubCategory(@PathVariable Long subCategoryId) {
		logger.info("Deleting subcategory with ID: {}", subCategoryId);
		subCategoryService.deleteSubCategory(subCategoryId);
		logger.info("Subcategory deleted with ID: {}", subCategoryId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/subcategory")
	public ResponseEntity<SubCategory> getSubCategoryByName(@RequestParam(name = "name") String subCategoryName) {
		logger.info("Getting subcategory by name: {}", subCategoryName);
		SubCategory subCategory = subCategoryService.getSubCategoryByName(subCategoryName);
		logger.info("Fetched subcategory: {}", subCategory);
		return new ResponseEntity<>(subCategory, HttpStatus.OK);
	}

	@GetMapping("/subcategory/names")
	public ResponseEntity<List<String>> getSubCategoryNameList() {
		logger.info("Getting list of subcategory names");
		List<String> list = subCategoryService.getAllSubcategoryNames();
		logger.info("Fetched {} subcategory names", list.size());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
