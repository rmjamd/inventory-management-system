package com.ramij.inventory.model;

import jakarta.persistence.*;

@Entity
public class Design {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long designId;

	private String designName;
	private String description;
	private String creatorName;

//	@ManyToOne
//	@JoinColumn(name = "subcategory_id")
//	private SubCategory subcategory;

//	@OneToMany(mappedBy = "design", cascade = CascadeType.ALL)
//	private List<Product> products;

}
