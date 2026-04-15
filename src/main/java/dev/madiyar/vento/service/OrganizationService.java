package dev.madiyar.vento.service;


import dev.madiyar.vento.dto.OrganizationCreateRequest;
import dev.madiyar.vento.dto.OrganizationResponse;
import dev.madiyar.vento.entity.Organization;
import dev.madiyar.vento.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;


    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public OrganizationResponse create(OrganizationCreateRequest request){
        Organization newOrganization = new Organization();
        newOrganization.setName(request.getName());
        Organization createdOrganization  = organizationRepository.save(newOrganization);
        return mapToResponse(createdOrganization);

    }

    public List<OrganizationResponse> getAll(){
        return organizationRepository.findAll()
                .stream()
                .map(org -> mapToResponse(org))
                .toList();
    }

    private OrganizationResponse mapToResponse(Organization organization){
        OrganizationResponse response = new OrganizationResponse();
        response.setName(organization.getName());
        response.setCreatedAt(organization.getCreatedAt());
        return response;
    }
}
