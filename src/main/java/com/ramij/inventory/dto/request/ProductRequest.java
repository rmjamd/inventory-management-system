package com.ramij.inventory.dto.request;

import com.ramij.inventory.model.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductRequest {
	private int          quantity;
	private BigDecimal   currentCost;
	private LocalDate    creationDate;
	private Long         designId;
	private Product.Size size;
}