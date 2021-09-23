package com.assem.blog.api;

import org.junit.jupiter.api.*;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.springframework.boot.test.context.SpringBootTest;
import com.assem.blog.BlogApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.assem.blog.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = BlogApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Sql(statements = {"INSERT INTO USERS VALUES ('a08adc9a150011ec82a80242ac130003',NULL,NULL,'Assem','123456','Assem bio');",
        "INSERT INTO USERS VALUES ('0871177b9b4a468495d396cf943e1e21',NULL,NULL,'Assem','123456','Assem bio');",
        "INSERT INTO USERS VALUES ('b3a53190150011ec82a80242ac130003',NULL,NULL,'Assem','123456','Assem bio');"}
)
@Sql(statements = "DELETE FROM users",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private UserDto userDto, newUserDto;

    private String userId;

    @BeforeEach
    void setUp() {
        userId = "a08adc9a-1500-11ec-82a8-0242ac130003";
        userDto = new UserDto(UUID.fromString(userId), "Assem", "123456", "BackEndDeveloper");
        newUserDto = new UserDto(null, "Assem", "123456", "BackEndDeveloper");
    }


    @Test
    @Order(1)
    void createUser() throws Exception {

        mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(newUserDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void getUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void CanGetUserById() throws Exception {
        mockMvc.perform(get("/users/{userId}", userId)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void CanUpdateUser() throws Exception {
        mockMvc.perform(put("/users/{userId}", userId)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(userDto))
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void CanDeleteUserById() throws Exception {
        mockMvc.perform(delete("/users/{userId}", userId)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}