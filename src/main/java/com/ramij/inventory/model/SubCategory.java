package com.ramij.inventory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Setter
@Getter
@ToString(exclude = {"designs","category"})
public class SubCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   subCategoryId;
	@Column(unique = true)
	private String subCategoryName;

	@ManyToOne(optional = false,fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId")
	private Category category;
	private String   description;

	@OneToMany(mappedBy = "subCategory",
			   cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List <Design> designs;

	Gender gender = Gender.UNISEX;

	public enum Gender {
		MALE, FEMALE, KIDS, UNISEX, BOY_KIDS, GIRL_KIDS
	}
}
