package com.ramij.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Setter
@Getter
@Accessors(chain = true)
@ToString(exclude = {"subcategories"})
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   categoryId;
	@Column(unique = true)
	private String categoryName;
	private String categoryDescription;

	@OneToMany(mappedBy = "category",
			   cascade = CascadeType.ALL,
			   fetch = FetchType.LAZY)
	@JsonIgnore
	private List <SubCategory> subcategories;

}