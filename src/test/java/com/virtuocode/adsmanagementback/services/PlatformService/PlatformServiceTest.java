package com.virtuocode.adsmanagementback.services.PlatformService;

import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToDeleteException;
import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToSaveException;
import com.virtuocode.adsmanagementback.Exceptions.EntityNotFoundException;
import com.virtuocode.adsmanagementback.dto.PlatformDto;
import com.virtuocode.adsmanagementback.entities.Platform;
import com.virtuocode.adsmanagementback.repositories.PlatformRepo;
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

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PlatformServiceTest {

    @Mock
    private PlatformRepo platformRepo;

    @InjectMocks
    private PlatformService platformService;

    @Test
    void shouldAddPlatform() {
        // given
        Platform platformToSave = Platform.builder()
                .name("platformName")
                .build();

        PlatformDto savedPlatformService = PlatformDto.builder()
                .id(1L)
                .name("platformName")
                .build();

        Platform savedPlatformRepo = Platform.builder()
                .id(1L)
                .name("platformName")
                .build();

        // mocking the save() repo
        when(platformRepo.save(Mockito.any(Platform.class))).thenReturn(savedPlatformRepo);

        // when
        PlatformDto res = platformService.addPlatform(platformToSave);

        // then
        assertThat(res).isEqualTo(savedPlatformService);
        // verify that save method is called with the correct platform
        verify(platformRepo, times(1)).save(platformToSave);
    }

    @Test
    void shouldThrowEntityFailedToSaveExceptionWhenSaveFails() {
        // given
        Platform platformToSave = Platform.builder()
                .name("platformName")
                .build();
        // mocking the save() repo
        when(platformRepo.save(Mockito.any(Platform.class))).thenThrow(RuntimeException.class);

        // then
        assertThrows(EntityFailedToSaveException.class, () -> platformService.addPlatform(platformToSave));

        verify(platformRepo, times(1)).save(platformToSave);
    }

    @Test
    void shouldDeletePlatform() {
        // given
        Long platformIdToDelete = 1L;

        PlatformDto deletedPlatformService = PlatformDto.builder()
                .id(1L)
                .name("platformName")
                .build();

        Platform findPlatformByIdRepo = Platform.builder()
                .id(1L)
                .name("platformName")
                .build();

        // mocking the deleteById() repo
        doNothing().when(platformRepo).deleteById(platformIdToDelete);
        // mocking the findById() repo
        when(platformRepo.findById(platformIdToDelete)).thenReturn(Optional.of(findPlatformByIdRepo));

        // when
        PlatformDto res = platformService.deletePlatform(platformIdToDelete);

        // then
        assertThat(res).isEqualTo(deletedPlatformService);
        // verify that deleteById method is called with the correct platform ID
        verify(platformRepo, times(1)).deleteById(platformIdToDelete);
        // verify that findById method is called with the correct platform ID
        verify(platformRepo, times(1)).findById(platformIdToDelete);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeleteFails(){
        //given
        Platform platformToDelete = Platform.builder()
                .id(1L)
                .name("platformName")
                .build();

        //when
        when(platformRepo.findById(platformToDelete.getId())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> platformService.deletePlatform(platformToDelete.getId()));

        verify(platformRepo, times(1)).findById(platformToDelete.getId());
    }

    @Test
    void shouldThrowEntityFailedToDeleteExceptionWhenDeleteFails() {
        // given
        Platform platformToDelete = Platform.builder()
                .id(1L)
                .build();

        //when
        when(platformRepo.findById(platformToDelete.getId())).thenReturn(Optional.of(platformToDelete));
        doThrow(RuntimeException.class).when(platformRepo).deleteById(platformToDelete.getId());

        // then
        assertThrows(EntityFailedToDeleteException.class, () -> platformService.deletePlatform(platformToDelete.getId()));

        verify(platformRepo, times(1)).findById(platformToDelete.getId());
        verify(platformRepo, times(1)).deleteById(platformToDelete.getId());
    }

    @Test
    void shouldUpdatePlatform() {
        // given
        Platform platformToUpdate = Platform.builder()
                .id(1L)
                .name("platformName")
                .build();

        PlatformDto updatedPlatformService = PlatformDto.builder()
                .id(1L)
                .name("platformName")
                .build();

        // mocking the save() repo
        when(platformRepo.save(Mockito.any(Platform.class))).thenReturn(platformToUpdate);
        // mocking the findById() repo
        when(platformRepo.findById(platformToUpdate.getId())).thenReturn(Optional.of(platformToUpdate));

        // when
        PlatformDto res = platformService.updatePlatform(platformToUpdate);

        // then
        assertThat(res).isEqualTo(updatedPlatformService);
        // verify that findById method is called with the correct platform ID
        verify(platformRepo, times(1)).findById(platformToUpdate.getId());
        // verify that save method is called with the correct platform
        verify(platformRepo, times(1)).save(platformToUpdate);
    }


    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdateFails(){
        //given
        Platform platformToUpdate = Platform.builder()
                .id(1L)
                .name("platformName")
                .build();

        //when
        when(platformRepo.findById(platformToUpdate.getId())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> platformService.updatePlatform(platformToUpdate));

        verify(platformRepo, times(1)).findById(platformToUpdate.getId());
    }

    @Test
    void shouldThrowEntityFailedToSaveExceptionWhenUpdateFails() {
        // given
        Platform platformToUpdate = Platform.builder()
                .id(1L)
                .name("platformName")
                .build();

        //when
        when(platformRepo.findById(platformToUpdate.getId())).thenReturn(Optional.of(platformToUpdate));
        when(platformRepo.save(Mockito.any(Platform.class))).thenThrow(RuntimeException.class);

        // then
        assertThrows(EntityFailedToSaveException.class, () -> platformService.updatePlatform(platformToUpdate));

        verify(platformRepo, times(1)).findById(platformToUpdate.getId());
        verify(platformRepo, times(1)).save(platformToUpdate);
    }

    @Test
    void shouldGetPlatforms() {
        // given
        // platform list from repository
        List<Platform> repoPlatformList = Arrays.asList(
                Platform.builder().id(1L).name("platformName1").build(),
                Platform.builder().id(2L).name("platformName2").build()
        );


        List<PlatformDto> servicePlatformList = repoPlatformList.stream().map(Platform::toDto).collect(Collectors.toList());

        // mocking the findAll() repo
        when(platformRepo.findAll()).thenReturn(repoPlatformList);

        // when
        List<PlatformDto> res = platformService.getPlatforms();

        // then
        assertThat(res).isEqualTo(servicePlatformList);
        // verify that findAll method is called
        verify(platformRepo, times(1)).findAll();
    }

    @Test
    void shouldGetPlatform() {
        // given
        Long platformIdToFind = 1L;

        PlatformDto findPlatformService = PlatformDto.builder()
                .id(1L)
                .name("platformName")
                .build();

        Platform findPlatformByIdRepo = Platform.builder()
                .id(1L)
                .name("platformName")
                .build();

        // mocking the findById() repo
        when(platformRepo.findById(platformIdToFind)).thenReturn(Optional.of(findPlatformByIdRepo));

        // when
        PlatformDto res = platformService.getPlatform(platformIdToFind);

        // then
        assertThat(res).isEqualTo(findPlatformService);
        // verify that findById method is called with the correct platform ID
        verify(platformRepo, times(1)).findById(platformIdToFind);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenGetPlatformFails() {
        // given
        Long platformIdToFind = 1L;

        // mocking the findById() repo
        when(platformRepo.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> platformService.getPlatform(platformIdToFind));
        verify(platformRepo, times(1)).findById(platformIdToFind);
    }
}
