package com.ramij.inventory.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SubCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subcategoryId;

	private String subCategoryName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "categoryId")
	private Category category;
	private String   description;

//	@OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
//	private List <Design> designs;
}
