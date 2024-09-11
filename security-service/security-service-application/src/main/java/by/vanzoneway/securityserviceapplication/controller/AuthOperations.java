package by.vanzoneway.securityserviceapplication.controller;

import by.vanzoneway.securityserviceapi.dto.request.SignInDto;
import by.vanzoneway.securityserviceapi.dto.response.JwtAuthenticationDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/default")
public interface AuthOperations {

    @PostMapping
    ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody @Valid SignInDto signInDto);
}
