package dev.madiyar.vento.controller;


import dev.madiyar.vento.dto.OrganizationCreateRequest;
import dev.madiyar.vento.dto.OrganizationResponse;
import dev.madiyar.vento.service.OrganizationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;


    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationResponse create(@RequestBody @Valid @NotNull OrganizationCreateRequest request){
        return organizationService.create(request);
    }
}
