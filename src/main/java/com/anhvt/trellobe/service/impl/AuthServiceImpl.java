package com.anhvt.trellobe.service.impl;

import com.anhvt.trellobe.advice.ErrorCode;
import com.anhvt.trellobe.advice.exception.AppException;
import com.anhvt.trellobe.configuration.MessageConfig;
import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.reponse.AuthResponse;
import com.anhvt.trellobe.dto.reponse.IntrospectResponse;
import com.anhvt.trellobe.dto.request.AuthRequest;
import com.anhvt.trellobe.dto.request.IntrospectRequest;
import com.anhvt.trellobe.dto.request.LogoutRequest;
import com.anhvt.trellobe.dto.request.RefreshRequest;
import com.anhvt.trellobe.entity.InvalidToken;
import com.anhvt.trellobe.entity.User;
import com.anhvt.trellobe.repository.InvalidTokenRepository;
import com.anhvt.trellobe.repository.UserRepository;
import com.anhvt.trellobe.service.AuthService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository userRepository;
    InvalidTokenRepository invalidTokenRepository;
    MessageConfig messageConfig;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    @Override
    public ServiceResult<AuthResponse> authenticate(AuthRequest request) {
        ServiceResult<AuthResponse> result = new ServiceResult<>();
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        if (!new BCryptPasswordEncoder(10).matches(request.getPassword(), user.getPassword())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);
        result.setData(AuthResponse.builder()
                        .token(token)
                        .authenticated(true)
                .build());
        result.setStatus(HttpStatus.OK);
        return result;
    }

    @Override
    public ServiceResult<IntrospectResponse> introspect(IntrospectRequest request) throws ParseException, JOSEException {
        ServiceResult<IntrospectResponse> result = new ServiceResult<>();
        var token = request.getToken();
        try {
            verifyToken(token);
            result.setData(IntrospectResponse.builder().valid(true).build());
        } catch (AppException e) {
            result.setData(IntrospectResponse.builder().valid(false).build());
        }
        result.setStatus(HttpStatus.OK);
        return result;
    }

    @Override
    public ServiceResult<?> logout(LogoutRequest request) throws ParseException, JOSEException {
        ServiceResult<?> result = new ServiceResult<>();
        var signToken = verifyToken(request.getToken());
        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidToken  invalidToken = InvalidToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidTokenRepository.save(invalidToken);
        result.setStatus(HttpStatus.OK);
        result.setMessage(messageConfig.getMessage("logout.success"));
        return result;
    }

    @Override
    public ServiceResult<AuthResponse> refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        ServiceResult<AuthResponse> result = new ServiceResult<>();
        var signJWT = verifyToken(request.getToken());
        var jit = signJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();
        InvalidToken invalidToken = InvalidToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidTokenRepository.save(invalidToken);

        var username = signJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.UNAUTHENTICATED));
        var token = generateToken(user);
        result.setData(AuthResponse.builder()
                        .authenticated(true)
                        .token(token)
                .build());
        result.setStatus(HttpStatus.OK);
        return result;
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);

        if(!(verified && expirationTime.after(new Date()))){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if(invalidTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    private String generateToken(User user){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("trellobe.anhvt.com")
                .issueTime(new Date())
                .expirationTime(Date.from(
                        Instant.now().plus(24, ChronoUnit.HOURS)
                ))
                .claim("scope", "pham vi cua token")
                .jwtID(UUID.randomUUID().toString())
                .claim("customClaim", "Custom")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }
}
