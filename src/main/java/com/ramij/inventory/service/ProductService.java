package com.ramij.inventory.service;

import com.ramij.inventory.dto.request.ProductRequest;
import com.ramij.inventory.dto.response.ProductResponse;
import com.ramij.inventory.exceptions.ResourceException;
import com.ramij.inventory.model.Design;
import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.model.Product;
import com.ramij.inventory.repository.DesignRepository;
import com.ramij.inventory.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ProductService {

	private final ProductRepository productRepository;
	private final DesignRepository  designService;


	@Autowired
	public ProductService (ProductRepository productRepository, DesignRepository designService) {
		this.productRepository = productRepository;
		this.designService     = designService;
	}


	@Transactional
	public ProductResponse createProduct (ProductRequest productRequest) {
		log.info("Creating a new product with details: {}", productRequest);
		try {
			Design design = designService.findByDesignName(productRequest.getDesignName());
			if (design == null) {
				throw new ResourceException(String.format("Design with name: %s not found", productRequest.getDesignName()));
			}
			Optional <Product> optionalProduct = productRepository.findByDesignIdAndProductSize(design, productRequest.getSize().getSizeName());
			Product            product;
			Product            updatedOrSavedProduct;
			if (optionalProduct.isEmpty()) {
				product = new Product();
				product.setQuantity(productRequest.getQuantity());
				product.setCurrentCost(productRequest.getCurrentCost());
				product.setCreationDate(LocalDate.now());
				product.setSize(productRequest.getSize());
				product.setDesign(design);
				updatedOrSavedProduct = productRepository.save(product);
				log.info("Created a new product with ID: {}", updatedOrSavedProduct.getProductId());
			} else {
				product = optionalProduct.get();
				product.setCurrentCost(productRequest.getCurrentCost());
				product.setQuantity(product.getQuantity() + productRequest.getQuantity());
				product.setCreationDate(LocalDate.now());
				updatedOrSavedProduct = productRepository.save(product);
				log.info("Updated productList with ID: {}", updatedOrSavedProduct.getProductId());
			}
			return mapProductToProductResponse(updatedOrSavedProduct);
		} catch (Exception ex) {
			log.error("Error occurred while creating a product: ", ex);
			throw new ResourceException("Error occurred to create a product");
		}
	}


	@Transactional(readOnly = true)
	public ProductResponse getProductById (Long productId) {
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
	public PageableItems <ProductResponse> getAllProducts (int pageNo, int size) {
		log.info("Getting all products");
		Page <Product> products   = productRepository.findAll(PageRequest.of(pageNo, size));
		int            totalPages = products.getTotalPages();
		List <ProductResponse> productList = products.getContent().stream()
													 .map(this::mapProductToProductResponse).toList();
		return new PageableItems <>(productList, totalPages);
	}


	@Transactional
	public void deleteProduct (Long productId) {
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


	private ProductResponse mapProductToProductResponse (Product product) {
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProductId(product.getProductId());
		productResponse.setQuantity(product.getQuantity());
		productResponse.setCurrentCost(product.getCurrentCost());
		productResponse.setCreationDate(product.getCreationDate());
		productResponse.setSize(product.getSize());
		productResponse.setDesignName(product.getDesign().getDesignName());
		return productResponse;
	}
}
