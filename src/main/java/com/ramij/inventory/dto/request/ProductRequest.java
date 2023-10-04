package com.ramij.inventory.dto.request;

import com.ramij.inventory.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
	private int          quantity;
	@NotNull
	private BigDecimal   currentCost;
	@NotNull
	private String       designName;
	@NotNull
	private Product.Size size;
}