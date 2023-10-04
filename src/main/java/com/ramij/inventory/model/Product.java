package com.ramij.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	Long productId;
	@Positive
	int  quantity;
	@Column(name = "cost")
	@NotNull(message = "Current cost cannot be null")
	private BigDecimal currentCost;
	@Column(name = "doc")
	private LocalDate  creationDate = LocalDate.now();
	@ManyToOne
	@JoinColumn(name = "design_id")
	@NotNull
	Design design;
	@NotNull
	private Size size;

	@Data
	public static class Size {
		private SizeName sizeName;
		private double   height;
		private double   width;

		public enum SizeName {
			M, X, L, XXL
		}

		// Constructors, getters, and setters
	}


}
