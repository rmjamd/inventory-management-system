package com.ramij.inventory.model;

import lombok.Getter;

import java.util.List;

@Getter
public class PageableItems <T> {
	final List <T> contents;
	final int      totalPages;


	public PageableItems (List <T> contents, int totalPages) {
		this.contents   = contents;
		this.totalPages = totalPages;
	}
}
