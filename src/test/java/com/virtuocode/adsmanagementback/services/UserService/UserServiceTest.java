package com.virtuocode.adsmanagementback.services.UserService;

import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToDeleteException;
import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToSaveException;
import com.virtuocode.adsmanagementback.Exceptions.EntityNotFoundException;
import com.virtuocode.adsmanagementback.dto.UserDto;
import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.repositories.UserRepo;
import com.virtuocode.adsmanagementback.shared.roles.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldAddUser() {
        // given
        User userToSave = User.builder()
                .username("username")
                .password("password")
                .role(UserRole.USER)
                .build();

        UserDto savedUserService = UserDto.builder()
                .id(1L)
                .username("username")
                .role(UserRole.USER)
                .build();

        User savedUserRepo = User.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(UserRole.USER)
                .build();

        // mocking the save() repo
        when(userRepo.save(Mockito.any(User.class))).thenReturn(savedUserRepo);

        // when
        UserDto res = userService.addUser(userToSave);


        // then
        assertThat(res).isEqualTo(savedUserService);
        // verify that save method is called with the correct user
        verify(userRepo, times(1)).save(userToSave);

    }

    @Test
    void shouldThrowEntityFailedToSaveExceptionWhenSaveFails() {
        // given
        User userToSave = User.builder()
                .username("username")
                .password("password")
                .role(UserRole.USER)
                .build();
        // mocking the save() repo
        when(userRepo.save(Mockito.any(User.class))).thenThrow(RuntimeException.class);

        // then
        assertThrows(EntityFailedToSaveException.class, () -> userService.addUser(userToSave));

        verify(userRepo, times(1)).save(userToSave);


    }

    @Test
    void shouldGetUsers() {
        // given
        // user list from repository
        List<User> repoUserList = Arrays.asList(
                User.builder().id(1L).username("username1").password("password1").role(UserRole.USER).build(),
                User.builder().id(2L).username("username2").password("password2").role(UserRole.USER).build()
        );

        // user list from service
        List<UserDto> serviceUserList = Arrays.asList(
                UserDto.builder().id(1L).username("username1").role(UserRole.USER).build(),
                UserDto.builder().id(2L).username("username2").role(UserRole.USER).build()
        );

        // mocking the findAll() repo
        when(userRepo.findAll()).thenReturn(repoUserList);

        // when
        List<UserDto> res = userService.getUsers();

        // then
        assertThat(res).isEqualTo(serviceUserList);
        // verify that findAll method is called
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void shouldDeleteUser() {
        // given
        Long userIdToDelete = 1L;

        UserDto deletedUserService = UserDto.builder()
                .id(1L)
                .username("username")
                .role(UserRole.USER)
                .build();

        User findUserByIdRepo = User.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(UserRole.USER)
                .build();

        // mocking the delete() repo
        doNothing().when(userRepo).delete(findUserByIdRepo);
        // mocking the findById() repo
        when(userRepo.findById(userIdToDelete)).thenReturn(Optional.of(findUserByIdRepo));

        // when
        UserDto res = userService.deleteUser(userIdToDelete);

        // then
        assertThat(res).isEqualTo(deletedUserService);
        // verify that delete method is called with the correct user
        verify(userRepo, times(1)).delete(findUserByIdRepo);
        // verify that findById method is called with the correct ID
        verify(userRepo, times(1)).findById(userIdToDelete);
    }

    @Test
    void shouldThrowEntityFailedToDeleteExceptionWhenDeleteFails() {
        // given
        User userToDelete = User.builder()
                .id(1L)
                .build();

        //when
        when(userRepo.findById(userToDelete.getId())).thenReturn(Optional.of(userToDelete));
        doThrow(RuntimeException.class).when(userRepo).delete(userToDelete);

        // then
        assertThrows(EntityFailedToDeleteException.class, () -> userService.deleteUser(userToDelete.getId()));

        verify(userRepo, times(1)).findById(userToDelete.getId());
        verify(userRepo, times(1)).delete(userToDelete);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeleteFails() {
        // given
        User userToDelete = User.builder()
                .id(1L)
                .build();

        //when
        when(userRepo.findById(userToDelete.getId())).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(userToDelete.getId()));

        verify(userRepo, times(1)).findById(userToDelete.getId());

    }


    @Test
    void shouldUpdateUser() {
        // given
        User userToUpdate = User.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(UserRole.USER)
                .build();

        User findUserByIdRepo = User.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(UserRole.USER)
                .build();

        UserDto updatedUserService = UserDto.builder()
                .id(1L)
                .username("username")
                .role(UserRole.USER)
                .build();

        User updatedUserRepo = User.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(UserRole.USER)
                .build();

        // mocking the findById() repo
        when(userRepo.findById(userToUpdate.getId())).thenReturn(Optional.of(findUserByIdRepo));
        // mocking the save() repo
        when(userRepo.save(Mockito.any(User.class))).thenReturn(updatedUserRepo);

        // when
        UserDto res = userService.updateUser(userToUpdate);

        // then
        assertThat(res).isEqualTo(updatedUserService);
        // verify that findById method is called with the correct ID
        verify(userRepo, times(1)).findById(userToUpdate.getId());
        // verify that save method is called with the correct user
        verify(userRepo, times(1)).save(Mockito.any(User.class));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdateFails() {
        // given
        User userToUpdate = User.builder()
                .id(1L)
                .build();

        //when
        when(userRepo.findById(userToUpdate.getId())).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(userToUpdate));

        verify(userRepo, times(1)).findById(userToUpdate.getId());

    }


    @Test
    void shouldThrowEntityFailedToSaveExceptionWhenUpdateFails() {
        //given
        User userToUpdate = User.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(UserRole.USER)
                .build();

        //when
        when(userRepo.findById(userToUpdate.getId())).thenReturn(Optional.of(userToUpdate));
        when(userRepo.save(Mockito.any(User.class))).thenThrow(RuntimeException.class);


        //then
        assertThrows(EntityFailedToSaveException.class, () -> userService.updateUser(userToUpdate));

        verify(userRepo, times(1)).save(userToUpdate);
        verify(userRepo, times(1)).findById(userToUpdate.getId());
    }

    @Test
    void shouldFindUser() {
        // given
        Long userIdToFind = 1L;

        UserDto findUserService = UserDto.builder()
                .id(1L)
                .username("username")
                .role(UserRole.USER)
                .build();

        User findUserByIdRepo = User.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(UserRole.USER)
                .build();

        // mocking the findById() repo
        when(userRepo.findById(userIdToFind)).thenReturn(Optional.of(findUserByIdRepo));

        // when
        UserDto res = userService.findUser(userIdToFind);

        // then
        assertThat(res).isEqualTo(findUserService);
        // verify that findById method is called with the correct ID
        verify(userRepo, times(1)).findById(userIdToFind);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenFindFails() {
        // given
        User userToFind = User.builder()
                .id(1L)
                .build();

        // mocking the findById() repo
        when(userRepo.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> userService.findUser(userToFind.getId()));
        verify(userRepo, times(1)).findById(userToFind.getId());
    }
}
