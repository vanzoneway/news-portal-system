package by.vanzoneway.securityserviceapplication.controller.impl;

import by.vanzoneway.securityserviceapi.dto.request.SignUpDto;
import by.vanzoneway.securityserviceapplication.controller.RegisterOperations;
import by.vanzoneway.securityservicecore.service.RegisterUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/signup")
@AllArgsConstructor
public class RegisterController implements RegisterOperations {

    private final RegisterUserService registerUserService;

    @Override
    public ResponseEntity<SignUpDto> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return ResponseEntity.ok(registerUserService.signUp(signUpDto));
    }

}
