package by.vanzoneway.clevertecgatewayservice.configuration;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final String secretKey = "mQ93h4D1I0t6P2n8vJ5x2v5nF1b3B9cVf45s7vW0t6Y=";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(jwt)
                        .getBody();

                String username = claims.getSubject();
                List<String> roles = claims.get("roles", List.class);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null,
                        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                log.error("JWT Token is invalid: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}






























//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    private final JwtTokenUtils jwtTokenUtils;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        String username = null;
//        String jwt = null;
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            jwt = authHeader.substring(7);
//            try {
//                username = jwtTokenUtils.getUsernameFromToken(jwt);
//            } catch (ExpiredJwtException e) {
//                log.debug("Token has expired");
//            }
//        }
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                    username,
//                    null,
//                    jwtTokenUtils.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
//            );
//            SecurityContextHolder.getContext().setAuthentication(token);
//        }
//        filterChain.doFilter(request, response);
//    }
//}