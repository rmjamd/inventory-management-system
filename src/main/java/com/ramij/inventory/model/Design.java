package com.ramij.inventory.model;

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
@ToString(exclude = {"subCategory", "products"})
public class Design {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   designId;
	@Column(unique = true)
	private String designName;
	private String description;
	private String creatorName;

	@ManyToOne(optional = false,fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_category_id")
	private SubCategory subCategory;

	@OneToMany(mappedBy = "design",
			   cascade = CascadeType.ALL)
	private List <Product> products;

}
