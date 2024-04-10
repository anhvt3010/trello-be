package com.anhvt.trellobe.resource;

import com.anhvt.trellobe.dto.ServiceResult;
import com.anhvt.trellobe.dto.reponse.AuthResponse;
import com.anhvt.trellobe.dto.reponse.IntrospectResponse;
import com.anhvt.trellobe.dto.request.AuthRequest;
import com.anhvt.trellobe.dto.request.IntrospectRequest;
import com.anhvt.trellobe.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/auth")
public class AuthResource {
    AuthService authService;
    @PostMapping("/token")
    public ResponseEntity<ServiceResult<AuthResponse>> login(@RequestBody AuthRequest request){
        ServiceResult<AuthResponse> result = authService.authenticate(request);
        return new ResponseEntity<>(result, result.getStatus());
    }

    @PostMapping("/introspect")
    public ResponseEntity<ServiceResult<IntrospectResponse>> verifyToken(@RequestBody IntrospectRequest request){
        ServiceResult<IntrospectResponse> result = authService.introspect(request);
        return new ResponseEntity<>(result, result.getStatus());
    }
}
