package com.ramij.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DesignRequest {
    @NotBlank(message = "Design name is required")
	private String designName;
	private String description;
	private String creatorName;
//	@NotBlank(message = "SubCategoryName name is required")
//    private String subCategoryName;
	// Constructors, getters, and setters
}
