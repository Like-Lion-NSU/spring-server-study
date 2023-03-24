package com.example.hana.Config;

import com.example.hana.Service.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
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
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserDetailsService userDetailsService;

    @Value("${jwt.token.key}")
    private String secretKey = "secretKey";
    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long tokenValidMillisecond = 1000L * 60 * 60; //토큰 유효시간

    @PostConstruct
    protected void init() {
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        secretKey = Encoders.BASE64.encode(key.getEncoded());
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    //JWT 토큰 생성
    public String createToken(String userId, List<String> roles) {
        LOGGER.info("[creatToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);//정보는 key/value 쌍으로 저장
        Date now = new Date();

        String token = Jwts.builder() //토큰 생성
                .setClaims(claims) //정보저장
                .setIssuedAt(now) //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey) //사용할 해싱 알고리즘, signature에 들어갈 secret값 세팅
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public Authentication getAuthentication(String token) {
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));

        logger.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}" , userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    public String getUsername(String token) {
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parserBuilder().setSigningKey(key).build()
                        .parseClaimsJws(token).getBody().getSubject();

        logger.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}" , info);
        return info;
    }

    public String resolveToken(HttpServletRequest request) {
        logger.info("[resolveToken] HTTP 헤더에서 token 값 추출");
        //return request.getHeader("X-AUTH-TOKEN");
        return request.getHeader("Authentication");//HttpServletRequest 를 파라미터로 받아서 헤더 값으로 전달된 Authentication를 리턴
    }

    public boolean validateToken(String token) {
        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
        try {
            //Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }
    }
}

