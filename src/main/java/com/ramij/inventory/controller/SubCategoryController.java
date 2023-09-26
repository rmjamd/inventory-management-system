package com.ramij.inventory.controller;

import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.model.SubCategory;
import com.ramij.inventory.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/subcategory")
public class SubCategoryController {
	final SubCategoryService subCategoryService;


	@Autowired
	public SubCategoryController (SubCategoryService subCategoryService) {
		this.subCategoryService = subCategoryService;
	}


	@PostMapping
	public ResponseEntity <SubCategory> createSubCategory (
			@RequestParam
			SubCategory subCategory) {
		SubCategory craetedSubCategory = subCategoryService.createSubCategory(subCategory);
		return new ResponseEntity <>(craetedSubCategory, HttpStatus.CREATED);
	}


	@GetMapping
	public ResponseEntity <PageableItems <SubCategory>> getAllSubcategory (
			@RequestParam(name = "page",
						  defaultValue = "0")
			int pageNo,
			@RequestParam(defaultValue = "5")
			int size) {
		PageableItems <SubCategory> pageableItems = subCategoryService.getAllSubCategory(pageNo, size);
		return new ResponseEntity <>(pageableItems, HttpStatus.OK);
	}


	@GetMapping("/{subCategoryId}")
	public ResponseEntity <SubCategory> getSubCategory (
			@PathVariable
			Long subCategoryId) {
		SubCategory subCategory = subCategoryService.getSubCategoryById(subCategoryId);
		return new ResponseEntity <>(subCategory, HttpStatus.OK);

	}


	@PutMapping("/{subCategoryId}")
	public ResponseEntity <SubCategory> updateSubCategory (
			@PathVariable
			Long subCategoryId,
			@RequestBody
			SubCategory updatedSubCategory) {
		SubCategory subCategory = subCategoryService.updateSubCategory(subCategoryId, updatedSubCategory);
		return new ResponseEntity <>(subCategory, HttpStatus.OK);
	}


	@DeleteMapping("/{subCategoryId}")
	public ResponseEntity <Void> deleteSubCategory (
			@PathVariable
			Long subCategoryId) {
		subCategoryService.deleteSubCategory(subCategoryId);
		return new ResponseEntity <>(HttpStatus.NO_CONTENT);
	}


	@GetMapping("/subCategory/{subCategoryName}")
	public ResponseEntity <SubCategory> getSubCategoryByName (
			@PathVariable
			String subCategoryName) {
		SubCategory subCategory = subCategoryService.getSubCategoryByName(subCategoryName);
		return new ResponseEntity <>(subCategory, HttpStatus.OK);
	}


}
