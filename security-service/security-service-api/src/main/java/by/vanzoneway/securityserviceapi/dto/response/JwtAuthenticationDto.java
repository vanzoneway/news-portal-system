package by.vanzoneway.securityserviceapi.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class JwtAuthenticationDto {

    private String token;
    private final String type = "Bearer";
    private String username;
    private String email;
    private List<String> roles;

}
