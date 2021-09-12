package com.assem.blog.dao;

import com.assem.blog.entity.User;
import com.assem.blog.exception.RecordNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void CheckIfUserExistsById() {
        //given
        User user = User.builder()
                .userName("assem")
                .password("123456")
                .bio("post writer")
                .build();
        underTest.save(user);

        //when
        User expectedUser = underTest.getById(user.getId());

        //then
        assertThat(expectedUser).isNotNull();
    }

    @Test
    void WillThrowWhenIdNotFound() {
        //given
        UUID randomId = UUID.randomUUID();

        //when
        Throwable thrown = catchThrowable(() -> {
            underTest.getById(randomId);
        });

        //then
        assertThat(thrown).isInstanceOf(RecordNotFoundException.class);
    }
}