package com.anhvt.trellobe.service;

import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.reponse.AuthResponse;
import com.anhvt.trellobe.dto.reponse.IntrospectResponse;
import com.anhvt.trellobe.dto.request.AuthRequest;
import com.anhvt.trellobe.dto.request.IntrospectRequest;
import com.anhvt.trellobe.dto.request.LogoutRequest;
import com.anhvt.trellobe.dto.request.RefreshRequest;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthService {
    ServiceResult<AuthResponse> authenticate(AuthRequest request);
    ServiceResult<IntrospectResponse> introspect(IntrospectRequest request) throws ParseException, JOSEException;
    ServiceResult<?> logout(LogoutRequest request) throws ParseException, JOSEException;
    ServiceResult<AuthResponse> refreshToken(RefreshRequest refreshRequest) throws ParseException, JOSEException;


}
