package com.ramij.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Data
@Accessors(chain = true)
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   categoryId;
	@Column(unique = true)
	private String categoryName;
	private String categoryDescription;

	@OneToMany(mappedBy = "category",
			   cascade = CascadeType.ALL)
	@JsonIgnore
	private List <SubCategory> subcategories;

}