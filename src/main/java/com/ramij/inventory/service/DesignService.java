package com.ramij.inventory.service;

import com.ramij.inventory.dto.request.DesignRequest;
import com.ramij.inventory.dto.response.DesignResponse;
import com.ramij.inventory.exceptions.ResourceException;
import com.ramij.inventory.model.Design;
import com.ramij.inventory.model.PageableItems;
import com.ramij.inventory.repository.DesignRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class DesignService {
	private final DesignRepository   designService;
	private final SubCategoryService subCategoryService;


	@Autowired
	public DesignService (DesignRepository designService, SubCategoryService subCategoryService) {
		this.designService      = designService;
		this.subCategoryService = subCategoryService;
	}


	public DesignResponse createDesign (DesignRequest request, String subCategoryName, MultipartFile image) {
		try {

			Design design = new Design();
			design.setDesignName(request.getDesignName());
			design.setDescription(request.getDescription());
			design.setCreatorName(request.getCreatorName());
			design.setSubCategory(subCategoryService.getSubCategoryByName(subCategoryName));
			design.setImage(design.getImage());
			design = designService.save(design);

			DesignResponse response = mapDesignToResponse(design);
			log.info("Created new design with ID: " + design.getDesignId());
			return response;
		} catch (Exception e) {
			log.error("Error creating design", e);
			throw new ResourceException("Failed to create design");
		}
	}


	public PageableItems <DesignResponse> getAllDesigns (int pageNo, int size) {
		try {
			Page <Design> pages   = designService.findAll(PageRequest.of(pageNo, size));
			List <Design> designs = pages.getContent();
			return new PageableItems <>(mapDesignsToResponses(designs), pages.getTotalPages());
		} catch (Exception e) {
			log.error("Error in fetching all design", e);
			throw new ResourceException("Error in fetching all design");
		}
	}


	public Optional <DesignResponse> getDesignById (Long id) {
		try {
			Optional <Design> designOptional = designService.findById(id);
			return designOptional.map(this::mapDesignToResponse);
		} catch (Exception e) {
			log.error("Error in fetching a design by id", e);
			throw new ResourceException("Error in fetching a design by id: "+id);
		}
	}


	public DesignResponse updateDesign (Long id, DesignRequest request) {
		try {
			Optional <Design> designOptional = designService.findById(id);
			if (designOptional.isEmpty()) {
				throw new EntityNotFoundException("Design not found with ID: " + id);
			}

			Design design = designOptional.get();
			design.setDesignName(request.getDesignName());
			design.setDescription(request.getDescription());
			design.setCreatorName(request.getCreatorName());
			design = designService.save(design);

			log.info("Updated design with ID: " + design.getDesignId());

			return mapDesignToResponse(design);
		} catch (Exception e) {
			log.error("Error updating design", e);
			throw new ResourceException("Failed to update design");
		}
	}


	public void deleteDesign (Long id) {
		try {
			designService.deleteById(id);
			log.info("Deleted design with ID: " + id);
		} catch (Exception e) {
			log.error("Error deleting design", e);
			throw new ResourceException("Failed to delete design");
		}
	}


	public DesignResponse getDesignByName (String name) {
		try {
			Design designName = designService.findByDesignName(name);
			return mapDesignToResponse(designName);
		} catch (Exception e) {
			log.error("Error in fetching a design by Name", e);
			throw new ResourceException("Error in fetching a design by Name");
		}
	}


	private List <DesignResponse> mapDesignsToResponses (List <Design> designs) {
		return designs.stream()
					  .map(this::mapDesignToResponse)
					  .toList();
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
