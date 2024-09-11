package by.vanzoneway.securityserviceapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignUpDto {

    @NotBlank(message = "Empty username")
    @Size(min = 4, max = 20, message = "Amount of character in username must be more then 4 and less then 20")
    private String username;

    @NotBlank(message = "Empty email")
    @Email(message = "Not supported format of email")
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 8, max = 30)
    private String password;

}
