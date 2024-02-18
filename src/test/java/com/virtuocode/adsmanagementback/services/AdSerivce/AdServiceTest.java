package com.virtuocode.adsmanagementback.services.AdSerivce;

import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToDeleteException;
import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToSaveException;
import com.virtuocode.adsmanagementback.Exceptions.EntityNotFoundException;
import com.virtuocode.adsmanagementback.dto.AdDto;
import com.virtuocode.adsmanagementback.entities.Ad;
import com.virtuocode.adsmanagementback.entities.Campaign;
import com.virtuocode.adsmanagementback.repositories.AdRepo;
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
class AdServiceTest {

    @Mock
    private AdRepo adRepo;

    @InjectMocks
    private AdService adService;

    @Test
    void shouldAddAd() {
        // given
        Ad adToSave = new Ad();
        adToSave.setTitle("Ad Title");

        AdDto savedAdService = new AdDto();
        savedAdService.setId(1L);
        savedAdService.setTitle("Ad Title");

        Ad savedAdRepo = new Ad();
        savedAdRepo.setId(1L);
        savedAdRepo.setTitle("Ad Title");

        // mocking the save() repo
        when(adRepo.save(Mockito.any(Ad.class))).thenReturn(savedAdRepo);

        // when
        AdDto res = adService.addAd(adToSave);

        // then
        assertThat(res).isEqualTo(savedAdService);
        // verify that save method is called with the correct ad
        verify(adRepo, times(1)).save(adToSave);
    }

    @Test
    void shouldThrowEntityFailedToSaveExceptionWhenSaveFails() {
        // given
        Ad adToSave = new Ad();
        adToSave.setTitle("Ad Title");
        // mocking the save() repo
        when(adRepo.save(Mockito.any(Ad.class))).thenThrow(RuntimeException.class);

        // then
        assertThrows(EntityFailedToSaveException.class, () -> adService.addAd(adToSave));

        verify(adRepo, times(1)).save(adToSave);
    }

    @Test
    void shouldGetAd(){
        // given
        Long adIdToFind = 1L;

        AdDto findAdService = new AdDto();
        findAdService.setId(1L);
        findAdService.setTitle("Ad Title");

        Ad findAdByIdRepo = new Ad();
        findAdByIdRepo.setId(1L);
        findAdByIdRepo.setTitle("Ad Title");

        // mocking the findById() repo
        when(adRepo.findById(adIdToFind)).thenReturn(Optional.of(findAdByIdRepo));

        // when
        AdDto res = adService.getAd(adIdToFind);

        // then
        assertThat(res).isEqualTo(findAdService);
        // verify that findById method is called with the correct ad ID
        verify(adRepo, times(1)).findById(adIdToFind);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenGetAdFails() {
        // given
        Long adIdToFind = 1L;

        // mocking the findById() repo
        when(adRepo.findById(adIdToFind)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> adService.getAd(adIdToFind));

        verify(adRepo, times(1)).findById(adIdToFind);
    }

    @Test
    void shouldGetAds() {
        // given
        List<Ad> repoAdList = Arrays.asList(
                new Ad(1L, "Ad Title 1", "Description 1", "Image URL 1", new Campaign()),
                new Ad(2L, "Ad Title 2", "Description 2", "Image URL 2", new Campaign())
        );

        List<AdDto> serviceAdList = repoAdList.stream().map(Ad::toDto).collect(Collectors.toList());

        // mocking the findAll() repo
        when(adRepo.findAll()).thenReturn(repoAdList);

        // when
        List<AdDto> res = adService.getAds();

        // then
        assertThat(res).isEqualTo(serviceAdList);
        // verify that findAll method is called
        verify(adRepo, times(1)).findAll();
    }
    @Test
    void shouldDeleteAd() {
        // given
        Long adIdToDelete = 1L;

        AdDto deletedAdService = new AdDto();
        deletedAdService.setId(1L);
        deletedAdService.setTitle("Ad Title");

        Ad findAdByIdRepo = new Ad();
        findAdByIdRepo.setId(1L);
        findAdByIdRepo.setTitle("Ad Title");

        // mocking the deleteById() repo
        doNothing().when(adRepo).deleteById(adIdToDelete);
        // mocking the findById() repo
        when(adRepo.findById(adIdToDelete)).thenReturn(Optional.of(findAdByIdRepo));

        // when
        AdDto res = adService.deleteAd(adIdToDelete);

        // then
        assertThat(res).isEqualTo(deletedAdService);
        // verify that deleteById method is called with the correct ad ID
        verify(adRepo, times(1)).deleteById(adIdToDelete);
        // verify that findById method is called with the correct ad ID
        verify(adRepo, times(1)).findById(adIdToDelete);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeleteFails() {
        // given
        Ad adToDelete = new Ad();
        adToDelete.setId(1L);

        //when
        when(adRepo.findById(adToDelete.getId())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> adService.deleteAd(adToDelete.getId()));

        verify(adRepo, times(1)).findById(adToDelete.getId());
    }

    @Test
    void shouldThrowEntityFailedToDeleteExceptionWhenDeleteFails() {
        // given
        Ad adToDelete = new Ad();
        adToDelete.setId(1L);

        //when
        when(adRepo.findById(adToDelete.getId())).thenReturn(Optional.of(adToDelete));
        doThrow(RuntimeException.class).when(adRepo).deleteById(adToDelete.getId());

        // then
        assertThrows(EntityFailedToDeleteException.class, () -> adService.deleteAd(adToDelete.getId()));

        verify(adRepo, times(1)).findById(adToDelete.getId());
        verify(adRepo, times(1)).deleteById(adToDelete.getId());
    }

    @Test
    void shouldUpdateAd() {
        // given
        Ad adToUpdate = new Ad();
        adToUpdate.setId(1L);
        adToUpdate.setTitle("Ad Title");

        AdDto updatedAdService = new AdDto();
        updatedAdService.setId(1L);
        updatedAdService.setTitle("Ad Title");

        // mocking the save() repo
        when(adRepo.save(Mockito.any(Ad.class))).thenReturn(adToUpdate);
        // mocking the findById() repo
        when(adRepo.findById(adToUpdate.getId())).thenReturn(Optional.of(adToUpdate));

        // when
        AdDto res = adService.updateAd(adToUpdate);

        //then
        assertThat(res).isEqualTo(updatedAdService);
        verify(adRepo, times(1)).findById(adToUpdate.getId());
    }


    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdateFails(){
        // given
        Ad adToUpdate = new Ad();
        adToUpdate.setId(1L);
        adToUpdate.setTitle("Ad Title");

        //when
        when(adRepo.findById(adToUpdate.getId())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> adService.updateAd(adToUpdate));

        verify(adRepo, times(1)).findById(adToUpdate.getId());
    }

    @Test
    void shouldThrowEntityFailedToSaveExceptionWhenUpdateFails() {
        // given
        Ad adToUpdate = new Ad();
        adToUpdate.setId(1L);
        adToUpdate.setTitle("Ad Title");

        //when
        when(adRepo.findById(adToUpdate.getId())).thenReturn(Optional.of(adToUpdate));
        doThrow(RuntimeException.class).when(adRepo).save(adToUpdate);

        // then
        assertThrows(EntityFailedToSaveException.class, () -> adService.updateAd(adToUpdate));

        verify(adRepo, times(1)).findById(adToUpdate.getId());
        verify(adRepo, times(1)).save(adToUpdate);
    }


}