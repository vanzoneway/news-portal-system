package by.vanzoneway.securityservicecore.service.impl;


import by.vanzoneway.securityserviceapi.dto.request.SignInDto;
import by.vanzoneway.securityserviceapi.dto.request.SignUpDto;
import by.vanzoneway.securityserviceapi.dto.response.JwtAuthenticationDto;
import by.vanzoneway.securityserviceapi.exceptions.user.UserInfoAlreadyExistsException;
import by.vanzoneway.securityserviceapi.mapper.UserMapper;
import by.vanzoneway.securityserviceapi.model.ERole;
import by.vanzoneway.securityserviceapi.model.Role;
import by.vanzoneway.securityserviceapi.model.User;
import by.vanzoneway.securityservicecore.repository.RoleRepository;
import by.vanzoneway.securityservicecore.repository.UserRepository;
import by.vanzoneway.securityservicecore.service.RegisterUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@ComponentScan(basePackages = {"by.vanzoneway.securityserviceapi"})
@RequiredArgsConstructor
public class RegisterUserServiceImpl implements RegisterUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    

    @Override
    public JwtAuthenticationDto signIn(SignInDto signInDto, Duration expirationTime, String secretKey) {
        Optional<User> user = userRepository.findByUsername(signInDto.getUsername());

        if (user.isEmpty())
            throw new UsernameNotFoundException("Username " + signInDto.getUsername() + " not found");
        if (!passwordEncoder.matches(signInDto.getPassword(), user.get().getPassword()))
            throw new BadCredentialsException("Wrong password");

        Map<String, Object> claims = new HashMap<>();

        UserDetails userDetails = UserDetailsImpl.build(user.get());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles", roles);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + expirationTime.toMillis());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();


        return JwtAuthenticationDto.builder()
                .token(token)
                .email(user.get().getEmail())
                .username(user.get().getUsername())
                .roles(roles)
                .build();
    }

    @Override
    public SignUpDto signUp(SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername()))
            throw new UserInfoAlreadyExistsException("Username already exists");
        if (userRepository.existsByEmail(signUpDto.getEmail()))
            throw new UserInfoAlreadyExistsException("Email already exists");
        User user = userMapper.toEntity(signUpDto);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));


        Set<Role> roles = signUpDto.getRole().stream()
                .map(strRole -> {
                    Role role = roleRepository.findByName(ERole.valueOf(strRole))
                            .orElseThrow(() -> new UserInfoAlreadyExistsException("Role does not exist"));

                    role.setName(ERole.valueOf(strRole));
                    roleRepository.save(role);
                    return role;
                })
                .collect(Collectors.toSet());


        user.setRoles(roles);
        userRepository.save(user);

        return signUpDto;
    }
}
