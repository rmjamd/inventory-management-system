package com.ramij.inventory.dto.response;

import com.ramij.inventory.model.Product;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class ProductResponse {
	private Long         productId;
	private int          quantity;
	private BigDecimal   currentCost;
	private LocalDate    creationDate;
	private String       designName;
	private Product.Size size;
}