package com.ramij.inventory.controller;

import com.ramij.inventory.dto.request.DesignRequest;
import com.ramij.inventory.dto.response.DesignResponse;
import com.ramij.inventory.service.DesignService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/design")
@Log4j2
public class DesignController {
	private final DesignService designService;


	@Autowired
	public DesignController (DesignService designService) {
		this.designService = designService;
	}


	@PostMapping
	public ResponseEntity <DesignResponse> createDesign (
			@RequestBody
			DesignRequest request) {

		// Service logic to create design
		DesignResponse response = designService.createDesign(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}


	@GetMapping
	public List <DesignResponse> getAllDesigns () {
		return designService.getAllDesigns();
	}


	@GetMapping("/{id}")
	public ResponseEntity <DesignResponse> getDesignById (
			@PathVariable
			Long id) {
		Optional <DesignResponse> designOptional = designService.getDesignById(id);
		return designOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}


	@PutMapping("/{id}")
	public ResponseEntity <DesignResponse> updateDesign (
			@PathVariable
			Long id,
			@RequestBody
			DesignRequest request) {

		DesignResponse response = designService.updateDesign(id, request);
		return ResponseEntity.ok(response);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity <Void> deleteDesign (
			@PathVariable
			Long id) {
		designService.deleteDesign(id);
		return ResponseEntity.noContent().build();
	}


	@GetMapping("/name/{name}")
	public ResponseEntity <DesignResponse> getDesignByName (
			@PathVariable
			String name) {
		Optional <DesignResponse> designOptional = designService.getDesignByName(name);
		return designOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}

