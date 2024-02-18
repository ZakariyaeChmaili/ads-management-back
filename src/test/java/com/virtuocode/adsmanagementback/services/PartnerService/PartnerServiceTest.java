package com.virtuocode.adsmanagementback.services.PartnerService;

import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToDeleteException;
import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToSaveException;
import com.virtuocode.adsmanagementback.Exceptions.EntityNotFoundException;
import com.virtuocode.adsmanagementback.dto.PartnerDto;
import com.virtuocode.adsmanagementback.entities.Partner;
import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.repositories.PartnerRepo;
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
class PartnerServiceTest {

    @Mock
    private PartnerRepo partnerRepo;

    @InjectMocks
    private PartnerService partnerService;

    @Test
    void shouldAddPartner() {
        // given
        Partner partnerToSave = new Partner();
        partnerToSave.setName("partnerName");

        PartnerDto savedPartnerService = new PartnerDto();
        savedPartnerService.setId(1L);
        savedPartnerService.setName("partnerName");

        Partner savedPartnerRepo = new Partner();
        savedPartnerRepo.setId(1L);
        savedPartnerRepo.setName("partnerName");

        // mocking the save() repo
        when(partnerRepo.save(Mockito.any(Partner.class))).thenReturn(savedPartnerRepo);

        // when
        PartnerDto res = partnerService.addParter(partnerToSave);

        // then
        assertThat(res).isEqualTo(savedPartnerService);
        // verify that save method is called with the correct partner
        verify(partnerRepo, times(1)).save(partnerToSave);
    }

    @Test
    void shouldThrowEntityFailedToSaveExceptionWhenSaveFails() {
        // given
        Partner partnerToSave = new Partner();
        partnerToSave.setName("partnerName");
        // mocking the save() repo
        when(partnerRepo.save(Mockito.any(Partner.class))).thenThrow(RuntimeException.class);

        // then
        assertThrows(EntityFailedToSaveException.class, () -> partnerService.addParter(partnerToSave));

        verify(partnerRepo, times(1)).save(partnerToSave);
    }

    @Test
    void shouldGetPartners() {
        // given
        // partner list from repository
        List<Partner> repoPartnerList = Arrays.asList(
                new Partner(1L, "partnerName1", "url1", "logo1", new User()),
                new Partner(2L, "partnerName2", "url2", "logo2", new User())
        );

        List<PartnerDto> servicePartnerList = repoPartnerList.stream().map(Partner::toDto).collect(Collectors.toList());

        // mocking the findAll() repo
        when(partnerRepo.findAll()).thenReturn(repoPartnerList);

        // when
        List<PartnerDto> res = partnerService.getPartners();

        // then
        assertThat(res).isEqualTo(servicePartnerList);
        // verify that findAll method is called
        verify(partnerRepo, times(1)).findAll();
    }

    @Test
    void shouldGetPartner() {
        // given
        Long partnerIdToFind = 1L;

        PartnerDto findPartnerService = new PartnerDto();
        findPartnerService.setId(1L);
        findPartnerService.setName("partnerName");

        Partner findPartnerByIdRepo = new Partner();
        findPartnerByIdRepo.setId(1L);
        findPartnerByIdRepo.setName("partnerName");

        // mocking the findById() repo
        when(partnerRepo.findById(partnerIdToFind)).thenReturn(Optional.of(findPartnerByIdRepo));

        // when
        PartnerDto res = partnerService.getPartner(partnerIdToFind);

        // then
        assertThat(res).isEqualTo(findPartnerService);
        // verify that findById method is called with the correct partner ID
        verify(partnerRepo, times(1)).findById(partnerIdToFind);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenGetPartnerFails() {
        // given
        Long partnerIdToFind = 1L;

        // mocking the findById() repo
        when(partnerRepo.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> partnerService.getPartner(partnerIdToFind));
        verify(partnerRepo, times(1)).findById(partnerIdToFind);
    }

    @Test
    void shouldDeletePartner() {
        // given
        Long partnerIdToDelete = 1L;

        PartnerDto deletedPartnerService = new PartnerDto();
        deletedPartnerService.setId(1L);
        deletedPartnerService.setName("partnerName");

        Partner findPartnerByIdRepo = new Partner();
        findPartnerByIdRepo.setId(1L);
        findPartnerByIdRepo.setName("partnerName");

        // mocking the delete() repo
        doNothing().when(partnerRepo).delete(findPartnerByIdRepo);
        // mocking the findById() repo
        when(partnerRepo.findById(partnerIdToDelete)).thenReturn(Optional.of(findPartnerByIdRepo));

        // when
        PartnerDto res = partnerService.deletePartner(partnerIdToDelete);

        // then
        assertThat(res).isEqualTo(deletedPartnerService);
        // verify that delete method is called with the correct partner ID
        verify(partnerRepo, times(1)).delete(findPartnerByIdRepo);
        // verify that findById method is called with the correct partner ID
        verify(partnerRepo, times(1)).findById(partnerIdToDelete);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeleteFails() {
        // given
        Partner partnerToDelete = new Partner();
        partnerToDelete.setId(1L);

        //when
        when(partnerRepo.findById(partnerToDelete.getId())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> partnerService.deletePartner(partnerToDelete.getId()));

        verify(partnerRepo, times(1)).findById(partnerToDelete.getId());
    }

    @Test
    void shouldThrowEntityFailedToDeleteExceptionWhenDeleteFails() {
        // given
        Partner partnerToDelete = new Partner();
        partnerToDelete.setId(1L);

        //when
        when(partnerRepo.findById(partnerToDelete.getId())).thenReturn(Optional.of(partnerToDelete));
        doThrow(RuntimeException.class).when(partnerRepo).delete(partnerToDelete);

        // then
        assertThrows(EntityFailedToDeleteException.class, () -> partnerService.deletePartner(partnerToDelete.getId()));

        verify(partnerRepo, times(1)).findById(partnerToDelete.getId());
        verify(partnerRepo, times(1)).delete(partnerToDelete);
    }

    @Test
    void shouldUpdatePartner() {
        // given
        Partner partnerToUpdate = new Partner();
        partnerToUpdate.setId(1L);
        partnerToUpdate.setName("partnerName");

        PartnerDto updatedPartnerService = new PartnerDto();
        updatedPartnerService.setId(1L);
        updatedPartnerService.setName("partnerName");

        // mocking the save() repo
        when(partnerRepo.save(Mockito.any(Partner.class))).thenReturn(partnerToUpdate);
        // mocking the findById() repo
        when(partnerRepo.findById(partnerToUpdate.getId())).thenReturn(Optional.of(partnerToUpdate));

        // when
        PartnerDto res = partnerService.updatePartner(partnerToUpdate);

        // then
        assertThat(res).isEqualTo(updatedPartnerService);
        // verify that findById method is called with the correct partner ID
        verify(partnerRepo, times(1)).findById(partnerToUpdate.getId());
        // verify that save method is called with the correct partner
        verify(partnerRepo, times(1)).save(partnerToUpdate);
    }


    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdateFails() {
        // given
        Partner partnerToUpdate = new Partner();
        partnerToUpdate.setId(1L);

        //when
        when(partnerRepo.findById(partnerToUpdate.getId())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> partnerService.updatePartner(partnerToUpdate));

        verify(partnerRepo, times(1)).findById(partnerToUpdate.getId());
    }

    @Test
    void shouldThrowEntityFailedToSaveExceptionWhenUpdateFails() {
        // given
        Partner partnerToUpdate = new Partner();
        partnerToUpdate.setId(1L);
        partnerToUpdate.setName("partnerName");

        //when
        when(partnerRepo.findById(partnerToUpdate.getId())).thenReturn(Optional.of(partnerToUpdate));
        when(partnerRepo.save(Mockito.any(Partner.class))).thenThrow(RuntimeException.class);

        // then
        assertThrows(EntityFailedToSaveException.class, () -> partnerService.updatePartner(partnerToUpdate));

        verify(partnerRepo, times(1)).findById(partnerToUpdate.getId());
        verify(partnerRepo, times(1)).save(partnerToUpdate);
    }
}
