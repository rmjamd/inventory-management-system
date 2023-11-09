package com.ramij.inventory.controller;

import com.ramij.inventory.dto.request.ProductRequest;
import com.ramij.inventory.dto.response.ProductResponse;
import com.ramij.inventory.model.Color;
import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.model.Product;
import com.ramij.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@Log4j2
public class ProductController {

	@Autowired
	private ProductService productService;


	@PostMapping("/product-list")
	public ResponseEntity <ProductResponse> createProduct (
			@RequestBody
			@Valid ProductRequest productRequest) {
		log.info("Creating a new product");
		ProductResponse productResponse = productService.createProduct(productRequest);
		return ResponseEntity.ok(productResponse);
	}


	@GetMapping("/product-list/{productId}")
	public ResponseEntity <ProductResponse> getProductById (
			@PathVariable
			Long productId) {
		log.info("Getting product by ID: {}", productId);
		ProductResponse productResponse = productService.getProductById(productId);
		return ResponseEntity.ok(productResponse);
	}


	@GetMapping("/product-lists")
	public ResponseEntity <PageableItems <ProductResponse>> getAllProducts (
			@RequestParam(name = "page",
						  defaultValue = "0",
						  required = false)
			int pageNo,
			@RequestParam(name = "size",
						  defaultValue = "5",
						  required = false)
			int size,
			@RequestParam(name = "designName",
						  required = false)
			String designName,
			@RequestParam(name = "color",
						  required = false)
			Color color,
			@RequestParam(name = "productSize",
						  required = false)
			Product.Size.SizeName productSize,

			@RequestParam(name = "sortBy",
						  defaultValue = "",
						  required = false)
			String sortBy
	) {
		log.info("Getting all products");
		PageableItems <ProductResponse> productResponses = productService.getAllProducts(pageNo, size, designName, color, productSize, sortBy);
		return ResponseEntity.ok(productResponses);
	}
	// Add other CRUD operations and mappings here
}
