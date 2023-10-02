package com.ramij.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	Long   productId;
	@Column(name = "des")
	String description;
	@Positive
	int    quantity;
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
	Gender gender;
}
