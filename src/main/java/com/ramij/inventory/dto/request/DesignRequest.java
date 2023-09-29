package com.ramij.inventory.dto.request;

import lombok.Data;

@Data
public class DesignRequest {
    @NotBlank(message = "Design name is required")
	private String designName;
	private String description;
	private String creatorName;
    private String subCategoryId;
    private String subCategoryName;
	// Constructors, getters, and setters
}
