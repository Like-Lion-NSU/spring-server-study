package com.springboot.todo.Config;

import com.springboot.todo.Dto.UserDetails;
import com.springboot.todo.Service.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;


    @Value("${spring.jwt.secret}")
    private String secretKey = "secretKey";
    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final Long tokenValidMillisecond = 1000L*60*60;


    @PostConstruct
    protected void init()
    {

        logger.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        secretKey = Encoders.BASE64.encode(key.getEncoded());
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        logger.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }


    public String createToken(String userUid, List<String> roles)
    {
        logger.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(userUid);
        claims.put("roles",roles);
        Date now = new Date(); //여기서 import 타입 Date이 달라서때문은 아니겟징
        String token = Jwts.builder()
                .setClaims(claims) //데이터
                .setIssuedAt(now) // 토큰 발행 일자
                .setExpiration(new Date(now.getTime()+ tokenValidMillisecond)) //만료 기간
                .signWith(key) //secret 값
                .compact(); // 토큰 생성
        logger.info("createToken 토큰 생성 완료");
        return token;
    }

    public Authentication getAuthentication(String token)
    {
        logger.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));

        logger.info("[getAuthentication] 토큰 인증 정보 조회 완료 UserDetails UserName : { }",
                userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());

    }

    public String getUsername(String token)
    {
        logger.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();

        logger.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}",info);
        return info;
    }

    public String resolveToken(HttpServletRequest request)
    {
        logger.info("[resolveToken] HTTP 헤더에서 Token 값 추출");


        return request.getHeader("X-AUTH-TOKEN");

    }

    public boolean validateToken(String token)
    {
        logger.info("[validateToken] 토큰 유효 체크 시작");
        try{
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            logger.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }
    }

}

