package com.virtuocode.adsmanagementback.services.CampaignService;

import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToDeleteException;
import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToSaveException;
import com.virtuocode.adsmanagementback.Exceptions.EntityNotFoundException;
import com.virtuocode.adsmanagementback.dto.CampaignDto;
import com.virtuocode.adsmanagementback.entities.Campaign;
import com.virtuocode.adsmanagementback.entities.Partner;
import com.virtuocode.adsmanagementback.repositories.CampaignRepo;
import com.virtuocode.adsmanagementback.shared.status.Status;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CampaignServiceTest {

    @Mock
    private CampaignRepo campaignRepo;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    void shouldGetCampaigns() {
        // given
        List<Campaign> repoCampaignList = Arrays.asList(
                Campaign.builder()
                        .id(1L)
                        .title("Campaign 1")
                        .description("Description 1")
                        .status(Status.DONE)
                        .partner(new Partner())
                        .build(),
                Campaign.builder()
                        .id(2L)
                        .title("Campaign 2")
                        .description("Description 2")
                        .status(Status.DRAFT)
                        .partner(new Partner())
                        .build()
        );

        List<CampaignDto> serviceCampaignList = repoCampaignList.stream().map(Campaign::toDto).collect(Collectors.toList());

        // mocking the findAll() repo
        when(campaignRepo.findAll()).thenReturn(repoCampaignList);

        // when
        List<CampaignDto> res = campaignService.getCampaigns();

        // then
        assertThat(res).isEqualTo(serviceCampaignList);
        // verify that findAll method is called
        verify(campaignRepo, times(1)).findAll();
    }

    @Test
    void shouldGetCampaign() {
        // given
        Long campaignIdToFind = 1L;

        CampaignDto findCampaignService = CampaignDto.builder()
                .id(1L)
                .title("Campaign 1")
                .description("Description 1")
                .status(Status.DONE)
                .build();

        Campaign findCampaignByIdRepo = Campaign.builder()
                .id(1L)
                .title("Campaign 1")
                .description("Description 1")
                .status(Status.DONE)
                .build();


        // mocking the findById() repo
        when(campaignRepo.findById(campaignIdToFind)).thenReturn(Optional.of(findCampaignByIdRepo));

        // when
        CampaignDto res = campaignService.getCampaign(campaignIdToFind);

        // then
        assertThat(res).isEqualTo(findCampaignService);
        // verify that findById method is called with the correct campaign ID
        verify(campaignRepo, times(1)).findById(campaignIdToFind);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenGetCampaignFails() {
        // given
        Long campaignIdToFind = 1L;

        // mocking the findById() repo
        when(campaignRepo.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> campaignService.getCampaign(campaignIdToFind));
        verify(campaignRepo, times(1)).findById(campaignIdToFind);
    }

    @Test
    void shouldAddCampaign() {
        // given
        Campaign campaignToSave = Campaign.builder()
                .title("Campaign Title")
                .build();

        CampaignDto savedCampaignService = CampaignDto.builder()
                .id(1L)
                .title("Campaign Title")
                .build();

        Campaign savedCampaignRepo = Campaign.builder()
                .id(1L)
                .title("Campaign Title")
                .build();


        // mocking the save() repo
        when(campaignRepo.save(Mockito.any(Campaign.class))).thenReturn(savedCampaignRepo);

        // when
        CampaignDto res = campaignService.addCampaign(campaignToSave);

        // then
        assertThat(res).isEqualTo(savedCampaignService);
        // verify that save method is called with the correct campaign
        verify(campaignRepo, times(1)).save(campaignToSave);
    }

    @Test
    void shouldThrowEntityFailedToSaveExceptionWhenSaveFails() {
        // given
        Campaign campaignToSave = new Campaign();
        campaignToSave.setTitle("Campaign Title");
        // mocking the save() repo
        when(campaignRepo.save(Mockito.any(Campaign.class))).thenThrow(RuntimeException.class);

        // then
        assertThrows(EntityFailedToSaveException.class, () -> campaignService.addCampaign(campaignToSave));

        verify(campaignRepo, times(1)).save(campaignToSave);
    }

    @Test
    void shouldDeleteCampaign() {
        // given
        Long campaignIdToDelete = 1L;

        CampaignDto deletedCampaignService = CampaignDto.builder()
                .id(1L)
                .title("Campaign Title")
                .build();

        Campaign findCampaignByIdRepo = Campaign.builder()
                .id(1L)
                .title("Campaign Title")
                .build();


        // mocking the deleteById() repo
        doNothing().when(campaignRepo).deleteById(campaignIdToDelete);
        // mocking the findById() repo
        when(campaignRepo.findById(campaignIdToDelete)).thenReturn(Optional.of(findCampaignByIdRepo));

        // when
        CampaignDto res = campaignService.deleteCampaign(campaignIdToDelete);

        // then
        assertThat(res).isEqualTo(deletedCampaignService);
        // verify that deleteById method is called with the correct campaign ID
        verify(campaignRepo, times(1)).deleteById(campaignIdToDelete);
        // verify that findById method is called with the correct campaign ID
        verify(campaignRepo, times(1)).findById(campaignIdToDelete);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeleteFails() {
        // given
        Campaign campaignToDelete = Campaign.builder()
                .id(1L)
                .build();

        //when
        when(campaignRepo.findById(campaignToDelete.getId())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> campaignService.deleteCampaign(campaignToDelete.getId()));

        verify(campaignRepo, times(1)).findById(campaignToDelete.getId());
    }

    @Test
    void shouldThrowEntityFailedToDeleteExceptionWhenDeleteFails() {
        // given
        Campaign campaignToDelete = Campaign.builder()
                .id(1L)
                .build();

        //when
        when(campaignRepo.findById(campaignToDelete.getId())).thenReturn(Optional.of(campaignToDelete));
        doThrow(RuntimeException.class).when(campaignRepo).deleteById(campaignToDelete.getId());

        // then
        assertThrows(EntityFailedToDeleteException.class, () -> campaignService.deleteCampaign(campaignToDelete.getId()));

        verify(campaignRepo, times(1)).findById(campaignToDelete.getId());
        verify(campaignRepo, times(1)).deleteById(campaignToDelete.getId());
    }

    @Test
    void shouldUpdateCampaign() {
        // given
        Campaign campaignToUpdate = Campaign.builder()
                .id(1L)
                .title("Campaign Title")
                .build();

        CampaignDto updatedCampaignService = CampaignDto.builder()
                .id(1L)
                .title("Campaign Title")
                .build();


        // mocking the save() repo
        when(campaignRepo.save(Mockito.any(Campaign.class))).thenReturn(campaignToUpdate);
        // mocking the findById() repo
        when(campaignRepo.findById(campaignToUpdate.getId())).thenReturn(Optional.of(campaignToUpdate));

        // when
        CampaignDto res = campaignService.updateCampaign(campaignToUpdate);

        // then
        assertThat(res).isEqualTo(updatedCampaignService);
        // verify that findById method is called with the correct campaign ID
        verify(campaignRepo, times(1)).findById(campaignToUpdate.getId());
        // verify that save method is called with the correct campaign
        verify(campaignRepo, times(1)).save(campaignToUpdate);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdateFails() {
        // given
        Campaign campaignToUpdate = Campaign.builder()
                .id(1L)
                .build();

        //when
        when(campaignRepo.findById(campaignToUpdate.getId())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> campaignService.updateCampaign(campaignToUpdate));

        verify(campaignRepo, times(1)).findById(campaignToUpdate.getId());
    }

    @Test
    void shouldThrowEntityFailedToSaveExceptionWhenUpdateFails() {
        // given
        Campaign campaignToUpdate =Campaign.builder()
                .id(1L)
                .title("Campaign Title")
                .build();

        //when
        when(campaignRepo.findById(campaignToUpdate.getId())).thenReturn(Optional.of(campaignToUpdate));
        when(campaignRepo.save(Mockito.any(Campaign.class))).thenThrow(RuntimeException.class);

        // then
        assertThrows(EntityFailedToSaveException.class, () -> campaignService.updateCampaign(campaignToUpdate));

        verify(campaignRepo, times(1)).findById(campaignToUpdate.getId());
        verify(campaignRepo, times(1)).save(campaignToUpdate);
    }
}
