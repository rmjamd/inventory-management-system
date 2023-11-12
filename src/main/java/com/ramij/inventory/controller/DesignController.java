package com.ramij.inventory.controller;

import com.ramij.inventory.dto.request.DesignRequest;
import com.ramij.inventory.dto.response.DesignResponse;
import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.service.DesignService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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


	@PostMapping(path = "/design",
				 consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public ResponseEntity <DesignResponse> createDesign (
			@NonNull
			@RequestParam(name = "subCategoryName")
			String subCategoryName,
			@RequestPart("image")
			MultipartFile image,
			@RequestPart("data")
			DesignRequest request) {
		log.info("Creating design for subCategoryName: {}", subCategoryName);
		// Service logic to create design
		DesignResponse response = designService.createDesign(request, subCategoryName, image);

		log.info("Design created successfully for subCategoryName: {}", subCategoryName);
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
		log.info("Fetching all designs with pageNo: {} and size: {}", pageNo, size);
		return designService.getAllDesigns(pageNo, size);
	}


	@GetMapping("/design/{id}")
	public ResponseEntity <DesignResponse> getDesignById (
			@PathVariable
			Long id) {
		log.info("Fetching design by ID: {}", id);
		Optional <DesignResponse> designOptional = designService.getDesignById(id);
		return designOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}


	@PutMapping("/design/{id}")
	public ResponseEntity <DesignResponse> updateDesign (
			@PathVariable
			Long id,
			@RequestBody
			DesignRequest request) {
		log.info("Updating design with ID: {}", id);

		DesignResponse response = designService.updateDesign(id, request);

		log.info("Design with ID {} updated successfully", id);
		return ResponseEntity.ok(response);
	}


	@DeleteMapping("/design/{id}")
	public ResponseEntity <Void> deleteDesign (
			@PathVariable
			Long id) {
		log.info("Deleting design with ID: {}", id);
		designService.deleteDesign(id);
		log.info("Design with ID {} deleted successfully", id);
		return ResponseEntity.noContent().build();
	}


	@GetMapping("/design")
	public ResponseEntity<DesignResponse> getDesignByName(
			@RequestParam(name = "name") String name) {
		log.info("Fetching design by Name: {}", name);
		DesignResponse design = designService.getDesignByName(name);
		return ResponseEntity.ok(design);
	}
	@GetMapping("/design/names")
	public ResponseEntity<List <String>> getAllDesignNames(){
		List <String> list = designService.getAllDesignNames();
		return new ResponseEntity <>(list, HttpStatus.OK);
	}


}
