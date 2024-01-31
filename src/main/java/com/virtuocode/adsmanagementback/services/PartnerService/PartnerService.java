package com.virtuocode.adsmanagementback.services.PartnerService;

import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToDeleteException;
import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToSaveException;
import com.virtuocode.adsmanagementback.Exceptions.EntityNotFoundException;
import com.virtuocode.adsmanagementback.dto.PartnerDto;
import com.virtuocode.adsmanagementback.entities.Partner;
import com.virtuocode.adsmanagementback.repositories.PartnerRepo;
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
            return savedPartner.toDto();
        } catch (Exception e) {
            throw new EntityFailedToSaveException(partner);
        }

    }

    @Override
    public List<PartnerDto> getPartners() {
        List<Partner> partners = this.partnerRepo.findAll();
        return partners.stream().map((Partner::toDto)).collect(Collectors.toList());
    }

    @Override
    public PartnerDto getPartner(Long partnerId) {
        return this.partnerRepo.findById(partnerId)
                .map(Partner::toDto)
                .orElseThrow(() -> new EntityNotFoundException(partnerId));
    }

    @Override
    public PartnerDto deletePartner(Long partnerId) {
        Partner partner = this.partnerRepo.findById(partnerId).orElseThrow(() -> new EntityNotFoundException(partnerId));
        try {
            this.partnerRepo.delete(partner);
            return partner.toDto();

        } catch (Exception e) {
            throw new EntityFailedToDeleteException(partner);
        }

    }


    @Override
    public PartnerDto updatePartner(Partner partner) {
        Partner existingPartner = this.partnerRepo.findById(partner.getId())
                .orElseThrow(() -> new EntityNotFoundException(partner.getId()));
        existingPartner.setName(partner.getName());
        existingPartner.setUrl(partner.getUrl());
        existingPartner.setLogo(partner.getLogo());
        existingPartner.setUser(partner.getUser());
        try {

            Partner updatedPartner = this.partnerRepo.save(existingPartner);
            return updatedPartner.toDto();

        } catch (Exception e) {
            throw new EntityFailedToSaveException(partner);
        }

    }
}
