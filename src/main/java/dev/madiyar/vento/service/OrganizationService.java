package dev.madiyar.vento.service;


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

    public void create(Organization organization){
        organizationRepository.save(organization);
    }

    public List<Organization> getAll(){
        return organizationRepository.findAll();
    }
}
