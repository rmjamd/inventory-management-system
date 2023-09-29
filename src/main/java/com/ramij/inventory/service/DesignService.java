package com.ramij.inventory.service;

import com.ramij.inventory.dto.request.DesignRequest;
import com.ramij.inventory.dto.response.DesignResponse;
import com.ramij.inventory.exceptions.ResourceException;
import com.ramij.inventory.model.Design;
import com.ramij.inventory.repository.DesignRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DesignService {
	private final DesignRepository   designRepository;
	private final SubCategoryService subCategoryService;


	@Autowired
	public DesignService (DesignRepository designRepository, SubCategoryService subCategoryService) {
		this.designRepository   = designRepository;
		this.subCategoryService = subCategoryService;
	}


	public DesignResponse createDesign (DesignRequest request) {
		try {

			Design design = new Design();
			design.setDesignName(request.getDesignName());
			design.setDescription(request.getDescription());
			design.setCreatorName(request.getCreatorName());
			design.setSubCategory(subCategoryService.getSubCategoryByName(request.getSubCategoryName()));
			design = designRepository.save(design);

			DesignResponse response = new DesignResponse();
			response.setDesignId(design.getDesignId());
			response.setDesignName(design.getDesignName());
			response.setDescription(design.getDescription());
			response.setCreatorName(design.getCreatorName());

			log.info("Created new design with ID: " + design.getDesignId());

			return response;
		} catch (Exception e) {
			log.error("Error creating design", e);
			throw new ResourceException("Failed to create design: " + e.getMessage());
		}
	}


	public List <DesignResponse> getAllDesigns () {
		try {
			List <Design> designs = designRepository.findAll();
			return mapDesignsToResponses(designs);
		} catch (Exception e) {
			log.error("Error in fetching all design", e);
			throw new ResourceException("Error in fetching all design: " + e.getMessage());
		}
	}


	public Optional <DesignResponse> getDesignById (Long id) {
		try {
			Optional <Design> designOptional = designRepository.findById(id);
			return designOptional.map(this::mapDesignToResponse);
		} catch (Exception e) {
			log.error("Error in fetching a design by id", e);
			throw new ResourceException("Error in fetching a design by id: " + e.getMessage());
		}
	}


	public DesignResponse updateDesign (Long id, DesignRequest request) {
		try {
			Optional <Design> designOptional = designRepository.findById(id);
			if (!designOptional.isPresent()) {
				throw new EntityNotFoundException("Design not found with ID: " + id);
			}

			Design design = designOptional.get();
			design.setDesignName(request.getDesignName());
			design.setDescription(request.getDescription());
			design.setCreatorName(request.getCreatorName());

			design = designRepository.save(design);

			log.info("Updated design with ID: " + design.getDesignId());

			return mapDesignToResponse(design);
		} catch (Exception e) {
			log.error("Error updating design", e);
			throw new ResourceException("Failed to update design: " + e.getMessage());
		}
	}


	public void deleteDesign (Long id) {
		try {
			designRepository.deleteById(id);
			log.info("Deleted design with ID: " + id);
		} catch (Exception e) {
			log.error("Error deleting design", e);
			throw new ResourceException("Failed to delete design: " + e.getMessage());
		}
	}


	public Optional <DesignResponse> getDesignByName (String name) {
		try {
			Optional <Design> designOptional = designRepository.findByDesignName(name);
			return designOptional.map(this::mapDesignToResponse);
		} catch (Exception e) {
			log.error("Error in fetching a design by Name", e);
			throw new ResourceException("Error in fetching a design by Name: " + e.getMessage());
		}
	}


	private List <DesignResponse> mapDesignsToResponses (List <Design> designs) {
		return designs.stream()
					  .map(this::mapDesignToResponse)
					  .collect(Collectors.toList());
	}


	private DesignResponse mapDesignToResponse (Design design) {
		DesignResponse response = new DesignResponse();
		response.setDesignId(design.getDesignId());
		response.setDesignName(design.getDesignName());
		response.setDescription(design.getDescription());
		response.setCreatorName(design.getCreatorName());
		return response;
	}
}
