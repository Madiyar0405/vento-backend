package dev.madiyar.vento.controller;


import dev.madiyar.vento.entity.Organization;
import dev.madiyar.vento.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;


    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }


    @GetMapping
    public List<Organization> getAll(){
        return organizationService.getAll();
    }

    @PostMapping("/create")
    public void create(@RequestBody Organization organization){
        organizationService.create(organization);
    }
}
