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


    // this is old legacy, can be shorten with lombok to @RequireArgsConstructor
    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }


    @GetMapping
    public List<Organization> getAll(){
        return organizationService.getAll();
    }

    // generally better to avoid the db entities especially, in certain cases it is security leak
    // in read mode still several additional issues better to use a custom input type and convert to db entity on service layer
    // validation missing the "name" is mandatory worth to check and fail fast before even db transaction starter in `repository.save`
    @PostMapping("/create")
    public void create(@RequestBody Organization organization){
        organizationService.create(organization);
    }
}
