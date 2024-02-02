package com.virtuocode.adsmanagementback.services.UserService;

import com.virtuocode.adsmanagementback.dto.UserDto;
import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.repositories.UserRepo;
import com.virtuocode.adsmanagementback.shared.roles.UserRole;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserServiceTest {


    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

        @Test
    void addUser() {
        //given
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

        //mocking the addUser() repo
        when(userRepo.save(userToSave)).thenReturn(savedUserRepo);

        //when
        UserDto res = userService.addUser(userToSave);


        //then
        assertThat(res).isEqualTo(savedUserService);
    }
    @Test
    void getUsers() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void findUser() {
    }
}