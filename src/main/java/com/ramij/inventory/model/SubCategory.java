package com.ramij.inventory.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SubCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subcategoryId;

	private String subcategoryName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "categoryId")
	private Category category;

//	@OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
//	private List <Design> designs;
}
