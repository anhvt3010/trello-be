package com.anhvt.trellobe.resource;

import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.UserDTO;
import com.anhvt.trellobe.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties") //nap h2DB
public class UserResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    private UserDTO userDTO;
    private ServiceResult<UserDTO> result;

    @BeforeEach
    void initData(){
        userDTO = UserDTO.builder()
                .email("user9@gmail.com")
                .username("user9")
                .displayName("User 9")
                .password("$2a$10$8OLE7we65..VjjaBNK29dOMQlD1MskJY6b./3vm1CTCs3lDBjA262")
                .build();

        result = new ServiceResult<>();
        result.setStatus(HttpStatus.CREATED);
        result.setMessage("Test save user success");
        result.setData(UserDTO.builder()
                        .id("id_test")
                        .email("user9@gmail.com")
                        .displayName("User 9")
//                        .password("$2a$10$8OLE7we65..VjjaBNK29dOMQlD1MskJY6b./3vm1CTCs3lDBjA262")
                        .build());
    }

    @Test
    void createUser_validReq_success() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(userDTO);

        Mockito.when(userService.save(ArgumentMatchers.any()))
                .thenReturn(result);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("CREATED"));


    }

    @Test
    void createUser_usernameInvalidReq_fail() throws Exception {
        userDTO.setUsername("aaa");
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(userDTO);

//        Mockito.when(userService.save(ArgumentMatchers.any()))
//                .thenReturn(result);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("username").value("size must be between 5 and 50"));

    }
}
