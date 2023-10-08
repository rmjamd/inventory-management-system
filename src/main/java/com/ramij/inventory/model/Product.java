package com.ramij.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Accessors(chain = true)
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private Size size;


	@Data
	@Embeddable
	public static class Size {
		private SizeName sizeName;
		private Double   height;
		private Double   width;

		public enum SizeName {
			M, X, L, XXL
		}

		// Constructors, getters, and setters
	}
}
