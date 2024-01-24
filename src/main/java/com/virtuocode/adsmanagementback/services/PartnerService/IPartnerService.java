package com.virtuocode.adsmanagementback.services.PartnerService;

import com.virtuocode.adsmanagementback.dto.PartnerDto;
import com.virtuocode.adsmanagementback.entities.Partner;

import java.util.List;

public interface IPartnerService {


    PartnerDto addParter(Partner partner);

    List<PartnerDto> getPartners();

    PartnerDto getPartner(Long partnerId);

    PartnerDto deletePartner(Long partnerId);


    PartnerDto updatePartner(Partner partner);
}
