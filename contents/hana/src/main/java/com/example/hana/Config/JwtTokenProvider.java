package com.example.hana.Config;

import com.example.hana.Service.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
//import lombok.Value;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
   // private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;

    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey";
    private final long tokenValidMillisecond = 1000L *60 *60;

    @PostConstruct
    protected void init(){
        LOGGER.info("[init] Jwt TokenProvider 내 secretKey 초기화 시작");
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    public String createToken(String userId, List<String> roles) {
        LOGGER.info("[creatToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);
        Date now = new Date();

        String token = Jwts.builder() //토큰 생성
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        LOGGER.info("[]createToken 토큰 생성 완료");
        return token;
    }

        public UsernamePasswordAuthenticationToken getAuthentication(String token){
            LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
            UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));

            LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}"+ userDetails.getUsername());
            return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
        }


    public String getUsername(String token) {
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody()
                .getSubject();
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}"+ info);
        return info;
    }

    public String resolveToken(HttpServletRequest request){
        LOGGER.info("[resolveToken] HTTP 헤더에서 token 값 추출");
        return request.getHeader("X-AUTH_TOKEN");
    }

    public boolean validateToken(String token){
        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            LOGGER.info("[validateToken] 토큰 유효 채크 예외 발생");
            return false;
        }
    }


}
