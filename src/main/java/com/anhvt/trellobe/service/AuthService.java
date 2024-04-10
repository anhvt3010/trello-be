package com.anhvt.trellobe.service;

import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.reponse.AuthResponse;
import com.anhvt.trellobe.dto.reponse.IntrospectResponse;
import com.anhvt.trellobe.dto.request.AuthRequest;
import com.anhvt.trellobe.dto.request.IntrospectRequest;

public interface AuthService {
    ServiceResult<AuthResponse> authenticate(AuthRequest request);
    public ServiceResult<IntrospectResponse> introspect(IntrospectRequest request);
}
