package com.ramij.inventory.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Data
@Accessors(chain = true)
public class Design {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   designId;
	@Column(unique = true)
	private String designName;
	private String description;
	private String creatorName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "sub_category_id")
	private SubCategory subCategory;

	@OneToMany(mappedBy = "design",
			   cascade = CascadeType.ALL)
	private List <Product> products;

}
