package com.anhvt.trellobe.service;

import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.UserDTO;
import com.anhvt.trellobe.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {
    ServiceResult<UserDTO> findOne(String id);
    ServiceResult<UserDTO> save(UserDTO userDTO);

}
