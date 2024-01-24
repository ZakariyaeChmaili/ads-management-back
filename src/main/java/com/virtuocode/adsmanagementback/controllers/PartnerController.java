package com.virtuocode.adsmanagementback.controllers;

import com.virtuocode.adsmanagementback.dto.PartnerDto;
import com.virtuocode.adsmanagementback.entities.Partner;
import com.virtuocode.adsmanagementback.services.PartnerService.IPartnerService;
import com.virtuocode.adsmanagementback.services.PartnerService.PartnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/partner")
public class PartnerController {

    private final IPartnerService partnerService;

    PartnerController(IPartnerService partnerService){

        this.partnerService = partnerService;
    }

    @GetMapping
    public ResponseEntity<List<PartnerDto>> getPartners(){
        List<PartnerDto> partners = partnerService.getPartners();
        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerDto> getPartner(@PathVariable Long id){
        PartnerDto partner = partnerService.getPartner(id);
        if (partner != null) {
            return new ResponseEntity<>(partner, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PartnerDto> addPartner(@RequestBody Partner partner){
        PartnerDto newPartner = partnerService.addParter(partner);
        return new ResponseEntity<>(newPartner, HttpStatus.CREATED);
    }


}
