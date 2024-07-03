package com.anhvt.trellobe.service.impl;

import com.anhvt.trellobe.advice.ErrorCode;
import com.anhvt.trellobe.advice.exception.AppException;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.UserDTO;
import com.anhvt.trellobe.entity.User;
import com.anhvt.trellobe.mapper.UserMapper;
import com.anhvt.trellobe.repository.UserRepository;
import com.anhvt.trellobe.service.BoardService;
import com.anhvt.trellobe.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    BoardService boardService;
    UserMapper userMapper;
    ModelMapper mapper;

    @Override
    public ServiceResult<UserDTO> findOne(String id) {
        ServiceResult<UserDTO> result =  new ServiceResult<>();
//        Optional<User> user = userRepository.findById(id);
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        result.setStatus(HttpStatus.OK);
        result.setData(mapper.map(user, UserDTO.class));
//        if (user.isPresent()) {
//            result.setStatus(HttpStatus.OK);
//            result.setData(mapper.map(user.get(), UserDTO.class));
//        } else {
//            result.setStatus(HttpStatus.NOT_FOUND);
//            result.setMessage("user.id.not_found");
//        }
        return result;
    }

    @Override
    public ServiceResult<UserDTO> save(UserDTO userDTO) {
        ServiceResult<UserDTO> result =  new ServiceResult<>();
        try {
            User user = userRepository.save(userMapper.toEntity(userDTO));
            result.setStatus(HttpStatus.CREATED);
            result.setData(userMapper.toDto(user));
            boardService.save(boardService.generateBoard(userDTO));
        } catch (Exception e) {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("user.create.bad_request");
        }
        return result;
    }
}
