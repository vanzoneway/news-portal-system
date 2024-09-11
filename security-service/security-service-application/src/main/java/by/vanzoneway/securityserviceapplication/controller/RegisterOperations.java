package by.vanzoneway.securityserviceapplication.controller;


import by.vanzoneway.securityserviceapi.dto.request.SignUpDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/default")
public interface RegisterOperations {

    @PostMapping
    ResponseEntity<SignUpDto> signUp(@RequestBody @Valid SignUpDto signUpDto);
}
