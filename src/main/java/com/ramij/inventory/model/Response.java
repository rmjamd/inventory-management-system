package com.ramij.inventory.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Response {
	String status;
	String message;
}
