package by.vanzoneway.securityservicecore.service;

import by.vanzoneway.securityserviceapi.dto.request.SignInDto;
import by.vanzoneway.securityserviceapi.dto.request.SignUpDto;
import by.vanzoneway.securityserviceapi.dto.response.JwtAuthenticationDto;

import java.time.Duration;

public interface RegisterUserService {

    JwtAuthenticationDto signIn(SignInDto signInDto, Duration expirationTime, String secretKey);

    SignUpDto signUp(SignUpDto signUpDto);

}
