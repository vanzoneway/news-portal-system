//package by.vanzoneway.clevertecgatewayservice.utils;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class JwtTokenUtils {
//
//    @Value("${spring.jwt.secret}")
//    private String secret;
//
//    public String getUsernameFromToken(String token) {
//        return getAllClaimsFromToken(token).getSubject();
//    }
//
//    public List<String> getRoles (String token) {
//        return getAllClaimsFromToken(token).get("roles", List.class);
//    }
//
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//}