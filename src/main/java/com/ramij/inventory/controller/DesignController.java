package com.ramij.inventory.controller;

import com.ramij.inventory.dto.request.DesignRequest;
import com.ramij.inventory.dto.response.DesignResponse;
import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.service.DesignService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Log4j2
public class DesignController {
	private final DesignService designService;


	@Autowired
	public DesignController (DesignService designService) {
		this.designService = designService;
	}


	@PostMapping("/design")
	public ResponseEntity <DesignResponse> createDesign (
			@NonNull
			@RequestParam(name = "subCategoryName")
			String subCategoryName,
			@RequestBody
			DesignRequest request) {

		// Service logic to create design
		DesignResponse response = designService.createDesign(request,subCategoryName);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}


	@GetMapping("/designs")
	public PageableItems <DesignResponse> getAllDesigns (
			@RequestParam(name = "page",
						  defaultValue = "0")
			int pageNo,
			@RequestParam(name = "size",
						  defaultValue = "5")
			int size) {
		return designService.getAllDesigns(pageNo, size);
	}


	@GetMapping("/design/{id}")
	public ResponseEntity <DesignResponse> getDesignById (
			@PathVariable
			Long id) {
		Optional <DesignResponse> designOptional = designService.getDesignById(id);
		return designOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}


	@PutMapping("/design/{id}")
	public ResponseEntity <DesignResponse> updateDesign (
			@PathVariable
			Long id,
			@RequestBody
			DesignRequest request) {

		DesignResponse response = designService.updateDesign(id, request);
		return ResponseEntity.ok(response);
	}


	@DeleteMapping("/design/{id}")
	public ResponseEntity <Void> deleteDesign (
			@PathVariable
			Long id) {
		designService.deleteDesign(id);
		return ResponseEntity.noContent().build();
	}


	@GetMapping("/design")
	public ResponseEntity <DesignResponse> getDesignByName (
			@PathVariable
			String name) {
		Optional <DesignResponse> designOptional = designService.getDesignByName(name);
		return designOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}

