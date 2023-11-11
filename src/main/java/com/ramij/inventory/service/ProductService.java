package com.ramij.inventory.service;

import com.ramij.inventory.dto.request.ProductRequest;
import com.ramij.inventory.dto.response.ProductResponse;
import com.ramij.inventory.exceptions.ResourceException;
import com.ramij.inventory.model.Color;
import com.ramij.inventory.model.Design;
import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.model.Product;
import com.ramij.inventory.repository.DesignRepository;
import com.ramij.inventory.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
			Optional <Product> optionalProduct = productRepository.findByDesignIdAndProductSize(productRequest.getDesignName(), productRequest.getSize().getSizeName(), productRequest.getColor(), productRequest.getCost());
			Product            product;
			Product            updatedOrSavedProduct;
			if (optionalProduct.isEmpty()) {
				product = new Product();
				product.setQuantity(productRequest.getQuantity());
				product.setCost(productRequest.getCost());
				product.setCreationDate(LocalDate.now());
				product.setSize(productRequest.getSize());
				product.setColor(productRequest.getColor());
				product.setDesignName(productRequest.getDesignName());
				updatedOrSavedProduct = productRepository.save(product);
				log.info("Created a new product with ID: {}", updatedOrSavedProduct.getProductId());
			} else {
				product = optionalProduct.get();
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
	public PageableItems <ProductResponse> getAllProducts (int pageNo, int size, String designName, Color color, Product.Size.SizeName productSize, String sortBy) {
		log.info("Getting all products with page: {}, size: {}, designName: {}, color: {}, sortBy: {}", pageNo, size, designName, color, sortBy);

		Sort     sort     = getSortBy(sortBy);
		Pageable pageable = PageRequest.of(pageNo, size, sort);

		Product exampleProduct = new Product();
		exampleProduct.setDesignName(designName);
		exampleProduct.setCreationDate(null);
		exampleProduct.setColor(color);
		exampleProduct.setSize(new Product.Size().setSizeName(productSize));

		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
													  .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Adjust this as needed

		Example <Product> example = Example.of(exampleProduct, exampleMatcher);

		Page <Product> products = productRepository.findAll(example, pageable);

		int totalPages = products.getTotalPages();
		List <ProductResponse> productList = products.getContent().stream()
													 .map(this::mapProductToProductResponse)
													 .toList();

		return new PageableItems <>(productList, totalPages);
	}
// The getSortBy, buildSpecification, and getDirection methods remain the same as in the previous response.


	private Sort getSortBy (String sortBy) {
		Sort sort = Sort.unsorted(); // Default to unsorted

		if (sortBy != null) {
			String[] sortParts = sortBy.split(",");
			for (String sortPart : sortParts) {
				String[] sortFieldAndDirection = sortPart.split("_");
				if (sortFieldAndDirection.length == 2) {
					String field     = sortFieldAndDirection[0];
					String direction = sortFieldAndDirection[1];

					if ("quantity".equals(field)) {
						sort = sort.and(Sort.by(Sort.Order.by(field).with(getDirection(direction))));
					} else if ("cost".equals(field)) {
						sort = sort.and(Sort.by(Sort.Order.by(field).with(getDirection(direction))));
					}
					else{
						throw new IllegalArgumentException("Invalid sort field: " + field);
					}
				}
			}
		}
		return sort;
	}


	private Sort.Direction getDirection (String direction) {
		if ("asc".equalsIgnoreCase(direction)) {
			return Sort.Direction.ASC;
		} else if ("desc".equalsIgnoreCase(direction)) {
			return Sort.Direction.DESC;
		}
		else{
			throw new IllegalArgumentException("Invalid sort direction: " + direction);
		}
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
		productResponse.setCost(product.getCost());
		productResponse.setCreationDate(product.getCreationDate());
		productResponse.setSize(product.getSize());
		productResponse.setDesignName(product.getDesignName());
		productResponse.setColor(product.getColor());
		return productResponse;
	}
}
