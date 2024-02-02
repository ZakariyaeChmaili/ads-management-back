package com.virtuocode.adsmanagementback.services.UserService;

import com.virtuocode.adsmanagementback.dto.UserDto;
import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.repositories.UserRepo;
import com.virtuocode.adsmanagementback.shared.roles.UserRole;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
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
        when(userRepo.save(userToSave)).thenReturn(savedUserRepo);

        // when
        UserDto res = userService.addUser(userToSave);

        // then
        assertThat(res).isEqualTo(savedUserService);
        // verify that save method is called with the correct user
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
        when(userRepo.save(userToUpdate)).thenReturn(updatedUserRepo);

        // when
        UserDto res = userService.updateUser(userToUpdate);

        // then
        assertThat(res).isEqualTo(updatedUserService);
        // verify that findById method is called with the correct ID
        verify(userRepo, times(1)).findById(userToUpdate.getId());
        // verify that save method is called with the correct user
        verify(userRepo, times(1)).save(userToUpdate);
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
}
