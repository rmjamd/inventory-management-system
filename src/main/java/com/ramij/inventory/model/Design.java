package com.ramij.inventory.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Design {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long designId;
	@Column(unique = true)
	private String designName;
	private String description;
	private String creatorName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "subCategoryId")
	private SubCategory subCategory;

//	@OneToMany(mappedBy = "design", cascade = CascadeType.ALL)
//	private List<Product> products;

}
