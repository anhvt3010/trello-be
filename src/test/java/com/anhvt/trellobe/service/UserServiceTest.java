package com.anhvt.trellobe.service;

import com.anhvt.trellobe.advice.ErrorCode;
import com.anhvt.trellobe.advice.exception.AppException;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.UserDTO;
import com.anhvt.trellobe.entity.User;
import com.anhvt.trellobe.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    private UserDTO userDTO;
    private User user;
    private ServiceResult<User> result;

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
        result.setData(User.builder()
                .id("id_test")
                .email("user9@gmail.com")
                .username("user9")
                .displayName("User 9")
//                        .password("$2a$10$8OLE7we65..VjjaBNK29dOMQlD1MskJY6b./3vm1CTCs3lDBjA262")
                .build());
    }

    @Test
    void createUser_validReq_success(){
        // GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(result.getData());

        // WHEN
        var res = userService.save(userDTO);

        // THEN
        Assertions.assertThat(res.getData().getId()).isEqualTo("id_test");
        Assertions.assertThat(res.getData().getEmail()).isEqualTo("user9@gmail.com");
        Assertions.assertThat(res.getData().getUsername()).isEqualTo("user9");
    }

    @Test
    void createUser_userExisted_fail(){
        userDTO.setUsername("user8");
        // GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(true);

        // WHEN
        var exception = org.junit.jupiter.api.Assertions.assertThrows(AppException.class,
                ()-> userService.save(userDTO));

        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_EXISTED);
    }
}
