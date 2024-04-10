package com.anhvt.trellobe.service;

import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.reponse.AuthResponse;
import com.anhvt.trellobe.dto.request.AuthRequest;

public interface AuthService {
    ServiceResult<AuthResponse> authenticate(AuthRequest request);
}
