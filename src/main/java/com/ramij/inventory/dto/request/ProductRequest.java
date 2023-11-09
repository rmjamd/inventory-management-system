package com.ramij.inventory.dto.request;

import com.ramij.inventory.model.Color;
import com.ramij.inventory.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ProductRequest {
	private int        quantity;
	@NotNull(message = "Current cost cannot be null")
	private BigDecimal cost;
	@NotNull(message = "Design name cannot be null")
	private String     designName;
	@NotNull(message = "Size name cannot be null")
	private Product.Size size;
	@NotNull(message = "Color cannot be null")
	Color color;
}