package com.ramij.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Accessors(chain = true)
@ToString(exclude = {"designName"})
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long productId;
	@Positive
	Integer  quantity;
	@Column(name = "cost")
	@NotNull(message = "Current cost cannot be null")
	private BigDecimal currentCost;
	@Column(name = "doc")
	private LocalDate  creationDate = LocalDate.now();
	@NotNull
	@Column(name = "design_name")
	private String designName;
	private Size size;
	private Color color;

	@Data
	@Embeddable
	public static class Size {
		@Enumerated(EnumType.STRING)
		private SizeName sizeName;
		private Double   height;
		private Double   width;

		public enum SizeName {
			M, X, L, XXL
		}

		// Constructors, getters, and setters
	}
}
