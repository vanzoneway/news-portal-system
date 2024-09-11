package by.vanzoneway.securityserviceapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {

    @NotBlank(message = "Empty username")
    private String username;

    @NotBlank(message = "Empty password")
    private String password;


}
