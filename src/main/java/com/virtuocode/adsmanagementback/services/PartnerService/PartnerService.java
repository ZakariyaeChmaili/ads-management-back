package com.virtuocode.adsmanagementback.services.PartnerService;

import com.virtuocode.adsmanagementback.dto.PartnerDto;
import com.virtuocode.adsmanagementback.entities.Partner;
import com.virtuocode.adsmanagementback.repositories.PartnerRepo;
import jdk.jfr.Experimental;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartnerService implements IPartnerService {


    private final PartnerRepo partnerRepo;

    PartnerService(PartnerRepo partnerRepo) {

        this.partnerRepo = partnerRepo;
    }


    @Override
    public PartnerDto addParter(Partner partner) {
        try {
            Partner savedPartner = this.partnerRepo.save(partner);
            return PartnerDto.builder()
                    .id(savedPartner.getId())
                    .name(savedPartner.getName())
                    .url(savedPartner.getUrl())
                    .logo(savedPartner.getLogo())
                    .build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<PartnerDto> getPartners() {
        try {
            List<Partner> partners = this.partnerRepo.findAll();
            return partners.stream()
                    .map(partner -> PartnerDto.builder()
                            .id(partner.getId())
                            .name(partner.getName())
                            .url(partner.getUrl())
                            .logo(partner.getLogo())
                            .user(partner.getUser())
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public PartnerDto getPartner(Long partnerId) {
        try {
            Optional<Partner> partnerOptional = this.partnerRepo.findById(partnerId);

            if (partnerOptional.isPresent()) {
                Partner existingPartner = partnerOptional.get();

                return PartnerDto.builder()
                        .id(existingPartner.getId())
                        .name(existingPartner.getName())
                        .url(existingPartner.getUrl())
                        .logo(existingPartner.getLogo())
                        .user(existingPartner.getUser())
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public PartnerDto deletePartner(Long partnerId) {
        try {
            Optional<Partner> partnerOptional = this.partnerRepo.findById(partnerId);

            if (partnerOptional.isPresent()) {
                Partner partnerToDelete = partnerOptional.get();
                this.partnerRepo.delete(partnerToDelete);

                return PartnerDto.builder()
                        .id(partnerToDelete.getId())
                        .name(partnerToDelete.getName())
                        .url(partnerToDelete.getUrl())
                        .logo(partnerToDelete.getLogo())
                        .user(partnerToDelete.getUser())
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public PartnerDto updatePartner(Partner partner) {
        try {
            Optional<Partner> existingPartnerOptional = this.partnerRepo.findById(partner.getId());

            if (existingPartnerOptional.isPresent()) {
                Partner existingPartner = existingPartnerOptional.get();

                existingPartner.setName(partner.getName());
                existingPartner.setUrl(partner.getUrl());
                existingPartner.setLogo(partner.getLogo());
                existingPartner.setUser(partner.getUser());

                Partner updatedPartner = this.partnerRepo.save(existingPartner);

                return PartnerDto.builder()
                        .id(updatedPartner.getId())
                        .name(updatedPartner.getName())
                        .url(updatedPartner.getUrl())
                        .logo(updatedPartner.getLogo())
                        .user(updatedPartner.getUser())
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
