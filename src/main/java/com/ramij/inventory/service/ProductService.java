package com.ramij.inventory.service;

import com.ramij.inventory.dto.request.ProductRequest;
import com.ramij.inventory.dto.response.ProductResponse;
import com.ramij.inventory.exceptions.ResourceException;
import com.ramij.inventory.model.Design;
import com.ramij.inventory.model.Product;
import com.ramij.inventory.repository.DesignService;
import com.ramij.inventory.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProductService {

	private final ProductRepository productRepository;
	private final DesignService     designService;

	@Autowired
	public ProductService(ProductRepository productRepository, DesignService designService) {
		this.productRepository = productRepository;
		this.designService     = designService;
	}

	@Transactional
	public ProductResponse createProduct(ProductRequest productRequest) {
		log.info("Creating a new product with details: {}", productRequest);

		Optional<Design> design = designService.findByDesignName(productRequest.getDesignName());
		if (design.isEmpty()) {
			throw new ResourceException(String.format("Design with name: %s not found", productRequest.getDesignName()));
		}

		try {
			Product product = new Product();
			product.setQuantity(productRequest.getQuantity());
			product.setCurrentCost(productRequest.getCurrentCost());
			product.setCreationDate(LocalDate.now());
			product.setSize(productRequest.getSize());
			product.setDesign(design.get());

			Product savedProduct = productRepository.save(product);
			log.info("Created a new product with ID: {}", savedProduct.getProductId());

			return mapProductToProductResponse(savedProduct);
		} catch (Exception ex) {
			log.error("Error occurred while creating a product: {}", ex.getMessage());
			throw new ResourceException("Error occurred to create a product");
		}
	}

	@Transactional(readOnly = true)
	public ProductResponse getProductById(Long productId) {
		log.info("Getting product by ID: {}", productId);

		try {
			Product product = productRepository.findById(productId)
											   .orElseThrow(() -> new ResourceException("Product not found with ID: " + productId));
			return mapProductToProductResponse(product);
		} catch (Exception ex) {
			log.error("Error occurred while getting a product by ID: {}", ex.getMessage());
			throw new ResourceException("Error occurred to get a product");
		}
	}

	@Transactional(readOnly = true)
	public List<ProductResponse> getAllProducts() {
		log.info("Getting all products");

		List<Product> products = productRepository.findAll();
		return products.stream()
					   .map(this::mapProductToProductResponse)
					   .collect(Collectors.toList());
	}

	@Transactional
	public void deleteProduct(Long productId) {
		log.info("Deleting product with ID: {}", productId);

		try {
			productRepository.deleteById(productId);
			log.info("Deleted product with ID: {}", productId);
		} catch (Exception ex) {
			log.error("Error occurred while deleting a product: {}", ex.getMessage());
			throw new ResourceException("Error occurred to delete a product");
		}
	}

	// Add other CRUD operations and mapping methods here

	private ProductResponse mapProductToProductResponse(Product product) {
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProductId(product.getProductId());
		productResponse.setQuantity(product.getQuantity());
		productResponse.setCurrentCost(product.getCurrentCost());
		productResponse.setCreationDate(product.getCreationDate());
		productResponse.setSize(product.getSize());
		// You may also set the design details here if needed.
		return productResponse;
	}
}
