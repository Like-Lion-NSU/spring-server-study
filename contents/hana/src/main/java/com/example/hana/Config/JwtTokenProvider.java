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


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.example.hana.Dto.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;
    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${jwt.token.key}")
    private String secretKey = "secretKey";
    private final long tokenValidMillisecond = 1000L * 60 * 60; //토큰 유효시간

    @PostConstruct
    protected void init() {
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");

        //secretKey를 Base64 형식으로 인코딩
        secretKey = Encoders.BASE64.encode(key.getEncoded());
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    //JWT 토큰 생성
    public String createToken(String userId, List<String> roles) {
        LOGGER.info("[creatToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);//클레임에 토큰 사용자의 권한을 확인할 role 추가
        Date now = new Date();

        String token = Jwts.builder() //토큰 생성
                .setClaims(claims) //정보저장
                .setIssuedAt(now) //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey) //사용할 해싱 알고리즘
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

     //인증 성공시 Authentication 생성
    public Authentication getAuthentication(String token) {
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));

        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    public String getUsername(String token) {
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        //secretKey 설정, sub 값 추출
        String info = Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();

        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
        return info;
    }

   //request의 헤더에서 token 값을 가져옴
    public String resolveToken(HttpServletRequest request) {
        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("Authorization");
    }

    //클레임의 유효기간 체크
    public boolean validateToken(String token) {
        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());

        } catch (Exception e) {
            LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }
    }
}
