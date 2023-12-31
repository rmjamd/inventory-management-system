package com.ramij.inventory.dto.response;

import com.ramij.inventory.model.Color;
import com.ramij.inventory.model.Product;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class ProductResponse {
	private Long         productId;
	private int        quantity;
	private BigDecimal cost;
	private LocalDate  creationDate;
	private Product.Size size;
	private     String designName;
	private Color  color;
}