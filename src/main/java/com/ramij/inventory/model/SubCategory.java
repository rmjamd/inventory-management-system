package com.ramij.inventory.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class SubCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   subCategoryId;
	@Column(unique = true)
	private String subCategoryName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "categoryId")
	private Category category;
	private String   description;

	@OneToMany(mappedBy = "subCategory",
			   cascade = CascadeType.ALL)
	private List <Design> designs;

	Gender gender = Gender.UNISEX;

	public enum Gender {
		MALE, FEMALE, KIDS, UNISEX, BOY_KIDS, GIRL_KIDS
	}
}
