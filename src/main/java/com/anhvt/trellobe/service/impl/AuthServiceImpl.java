package com.anhvt.trellobe.service.impl;

import com.anhvt.trellobe.advice.ErrorCode;
import com.anhvt.trellobe.advice.exception.AppException;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.reponse.AuthResponse;
import com.anhvt.trellobe.dto.request.AuthRequest;
import com.anhvt.trellobe.entity.User;
import com.anhvt.trellobe.repository.UserRepository;
import com.anhvt.trellobe.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository userRepository;
    @Override
    public ServiceResult<AuthResponse> authenticate(AuthRequest request) {
        ServiceResult<AuthResponse> result = new ServiceResult<>();
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        result.setData(AuthResponse.builder()
                        .authenticated(new BCryptPasswordEncoder(10)
                                .matches(request.getPassword(), user.getPassword()))
                        .build());
        result.setStatus(HttpStatus.OK);
        return result;
    }
}
