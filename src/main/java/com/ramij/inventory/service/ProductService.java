package com.ramij.inventory.service;


import com.ramij.inventory.dto.request.ProductRequest;
import com.ramij.inventory.dto.response.ProductResponse;
import com.ramij.inventory.model.Product;
import com.ramij.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;


	@Transactional
	public ProductResponse createProduct (ProductRequest productRequest) {
		Product product = new Product();
		product.setQuantity(productRequest.getQuantity());
		product.setCurrentCost(productRequest.getCurrentCost());
		product.setCreationDate(productRequest.getCreationDate());
		product.setSize(productRequest.getSize());
		// Set the Design by fetching it from the database or another source.
		// product.setDesign(...);

		Product savedProduct = productRepository.save(product);
		log.info("Created a new product with ID: {}", savedProduct.getProductId());

		return mapProductToProductResponse(savedProduct);
	}


	@Transactional(readOnly = true)
	public ProductResponse getProductById (Long productId) {
		Product product = productRepository.findById(productId)
										   .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
		return mapProductToProductResponse(product);
	}


	@Transactional(readOnly = true)
	public List <ProductResponse> getAllProducts () {
		List <Product> products = productRepository.findAll();
		return products.stream()
					   .map(this::mapProductToProductResponse)
					   .collect(Collectors.toList());
	}

	// Add other CRUD operations and mapping methods here


	private ProductResponse mapProductToProductResponse (Product product) {
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProductId(product.getProductId());
		productResponse.setQuantity(product.getQuantity());
		productResponse.setCurrentCost(product.getCurrentCost());
		productResponse.setCreationDate(product.getCreationDate());
		productResponse.setSize(product.getSize());
		// Set the design ID if needed.
		// productResponse.setDesignId(product.getDesign().getId());
		return productResponse;
	}
}
