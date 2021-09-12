package com.assem.blog.service;

import com.assem.blog.dao.UserRepository;
import com.assem.blog.dto.UserDto;
import com.assem.blog.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;
    private User user;
    //    private AutoCloseable autoCloseable;  --> @ExtendWith(MockitoExtension.class)

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        user = User.builder()
                .userName("assem")
                .password("123456")
                .bio("post writer")
                .build();
        //  autoCloseable = MockitoAnnotations.openMocks(this); --->@ExtendWith(MockitoExtension.class)
    }

    /*
        @AfterEach
        void tearDown() throws Exception {
            autoCloseable.close(); -->@ExtendWith(MockitoExtension.class)
        }
    */

    @Test
    void CanGetAllUsers() {
        //when
        userService.findAll();
        //then
        verify(userRepository).findAll();
    }

    @Test
    public void CanFindUserById() {
        //given
        when(userRepository.getById(user.getId())).thenReturn(user);

        //when
        userService.findById(user.getId());

        //then
        ArgumentCaptor<UUID> uuidArgumentCaptor =
                ArgumentCaptor.forClass(UUID.class);
        verify(userRepository)
                .getById(uuidArgumentCaptor.capture());
        //then
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(user.getId());
    }

    @Test
    void CanCreateNewUser() {
        //given
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        //when
        userService.create(user.asDTO());

        //then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository)
                .save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getUserName()).isEqualTo(user.getUserName());
        assertThat(capturedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(capturedUser.getBio()).isEqualTo(user.getBio());
    }

    @Test
    void CanUpdateExistedUser() {
        //given
        when(userRepository.getById(user.getId())).thenReturn(user);

        User updatedUser = User.builder()
                .userName("Updated UserName")
                .password("123456")
                .bio("post writer")
                .build();
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(updatedUser);

        //when
        userService.update(user.getId(), user.asDTO());

        //then
        ArgumentCaptor<UUID> uuidArgumentCaptor =
                ArgumentCaptor.forClass(UUID.class);

        verify(userRepository)
                .getById(uuidArgumentCaptor.capture());

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository)
                .save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(user.getId());

        assertThat(capturedUser.getPassword()).isNotEqualTo(updatedUser.getUserName());
        assertThat(capturedUser.getPassword()).isEqualTo(updatedUser.getPassword());
        assertThat(capturedUser.getBio()).isEqualTo(updatedUser.getBio());

    }

    @Test
    void CanDeleteExistedUser() {
        //given
        UUID userId = user.getId();

        //when
        userService.delete(userId);

        //then
        ArgumentCaptor<UUID> uuidArgumentCaptor =
                ArgumentCaptor.forClass(UUID.class);

        verify(userRepository)
                .getById(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(userId);
    }

    @Test
    @Disabled
    void follow() {
    }

    @Test
    @Disabled
    void unfollow() {
    }
}