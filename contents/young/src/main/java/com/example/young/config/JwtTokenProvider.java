package com.example.young.config;

import com.example.young.Service.UserDetailsSerivce;
import com.example.young.entity.UserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.jsonwebtoken.Claims;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.Jws;
import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);    //JwtTokenProvider.class에 Loggin 주입
    private final UserDetailsSerivce userDetailsSerivce;    // userDetailsSerivce 객체 생성
    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);        // 토큰 암호화 키  * HMAC 이용

    @Value("${spring.jwt.secret}")      // spring.jwt.sercret 값을 설정파일(ex. aplication properties)에 주입
    private String secretKey = "secretKey";     // secretKey 정의
    private final long tokenValidMillisecond = 1000L * 60 * 60 * 24;        // 토큰 유효 시간
    @PostConstruct
    /* 의존성 주입이 완료된 후에 실해되는 메서드에 사용
     생성자 호출 -> bean 초기화 전 -> @PostConstruct 사용 시 bean이 초기화 됨과 동시에 의존성 확인 가능 */
    protected void init(){
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        secretKey = Encoders.BASE64.encode(key.getEncoded());   // secretKey를 Base64 형식으로 인코딩
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        // Kesy.hmacShaKeyFor : 환경설정에 있는 비밀키 문자열을 바이트배열로 전달 하여 SecretKey 인스턴스 생성
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    public String createToken(String userid, List<String> roles){
        LOGGER.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(userid);   // JWT 토큰의 내용에 값을 넣기 위해 Claims 객체 생성
        claims.put("roles", roles);     // 해당 토큰 사용하는 사용자의 권한 확인 = role
        Date now = new Date();

        String token = Jwts.builder()   // builder를 사용해 token 생성
                .setClaims(claims)
                .setIssuedAt(now)   // 토큰 발급 시간
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 암호화 알고리즘, secret 값 세팅 => 서명
                .compact();     // 토크 생성

        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public Authentication getAuthentication(String token){      // Authentication 생성
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsSerivce.loadUserByUsername(this.getUsername(token));
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "Bearer", userDetails.getAuthorities());
          /* 상속받는 AbstractAuthenticationToken 클래스를 사용하기 위한 초기화를 진행하기 위해 UserDetails 사용
            이는 UserDetailsService를 통해 가져옴 */
    }

    public String getUsername(String token){
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
        // Jwts.parserBuilder()를 통해 secreKey를 설정하고 클레임을 추출해서 토큰 생성 시 넣었던 sub값을 추출
        // sub = 토큰 기반 회원 구별 정보
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}",info);
        return info;
    }


    public String resolveToken(HttpServletRequest request){
        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("Authorization");
        // 파라미터로 받은 HttpServletRequset의 헤더 값으로 전달된 'Authorization' 값을 가져옴
    }

    public boolean validateToken(String token){     // 클레임의 유효기간 체크
        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);  // Decode해서 데이터를 얻어냄

            return !claims.getBody().getExpiration().before(new Date());    // 현재 시간이 만료되지 않으면 true?
        }catch(Exception e){
            LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }
    }
}
