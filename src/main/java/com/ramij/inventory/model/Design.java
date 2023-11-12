package com.ramij.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Setter
@Getter
@Accessors(chain = true)
@ToString(exclude = {"subCategory","image"})
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
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	@Size(max = 512400, message = "Image size should not exceed 100KB")
	private byte[] image;
}
