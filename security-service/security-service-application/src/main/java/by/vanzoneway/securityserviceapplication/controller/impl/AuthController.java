package by.vanzoneway.securityserviceapplication.controller.impl;

import by.vanzoneway.securityserviceapi.dto.request.SignInDto;
import by.vanzoneway.securityserviceapi.dto.response.JwtAuthenticationDto;
import by.vanzoneway.securityserviceapplication.controller.AuthOperations;
import by.vanzoneway.securityservicecore.service.RegisterUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/signin")
@RequiredArgsConstructor
public class AuthController implements AuthOperations {

    private final RegisterUserService registerUserService;

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @Value("${spring.jwt.expiration}")
    private Duration EXPIRED_TIME;

    @Override
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(registerUserService.signIn(signInDto, EXPIRED_TIME, SECRET_KEY));
    }
}
