package com.neotech.controller;

import com.neotech.service.CountryCodeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PhoneNumberController {


    @Autowired
    private CountryCodeService countryCodeService;

    @GetMapping("/api/get-country")
    @ApiOperation(value = "validate phone number and return country")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    public ResponseEntity getApplications(String phone) {
        return ResponseEntity.ok(countryCodeService.get(phone));
    }

}
