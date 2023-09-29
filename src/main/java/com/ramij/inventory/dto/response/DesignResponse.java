package com.ramij.inventory.dto.response;

import lombok.Data;

@Data
public class DesignResponse {
	private Long   designId;
	private String designName;
	private String description;
	private String creatorName;

	// Constructors, getters, and setters
}
