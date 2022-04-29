package by.itechart.film2nightApp.security.jwt;

import by.itechart.film2nightApp.entity.Role;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JWTTokenProvider {

    private  static String KEYWORD = "SECRET";
    private final static long  VALIDITY_IN_MILISECONDS = 360000000;



    private final UserDetailsService userDetailsService;

    @Autowired
    public JWTTokenProvider(@Qualifier("JWTUserDetailsService")@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init(){
        KEYWORD = Base64.getEncoder().encodeToString(KEYWORD.getBytes());
    }

    public String createToken(String username, List<Role> role){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles",getRoleNames(role));

        Date date = new Date();
        Date validity = new Date(date.getTime() + VALIDITY_IN_MILISECONDS);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,KEYWORD)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(KEYWORD).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KEYWORD).parseClaimsJws(token);
            if(claimsJws.getBody().getExpiration().before(new Date())){
                log.info("validate token - false");
                return false;
            }
            log.info("validate token - true");
            return true;
        }catch (JwtException | IllegalArgumentException e){
            throw new JwtException("jwt token is expired or invalid");
        }
    }

    private List<String> getRoleNames(List<Role> userRoles){
        List<String> result = new ArrayList<>();
        userRoles.forEach(role -> result.add(role.getName()));
        return result;
    }
}
