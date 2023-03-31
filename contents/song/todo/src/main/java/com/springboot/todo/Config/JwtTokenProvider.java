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

//토큰 생성, 검증 등의 함수들을 구현해놓은 클래스, JWT에서 핵심이 되는 클래스
/*
* 클라이언트측에서부터 서버측으로 JWT 받음
* 서버측의 비밀 값과 JWT의 헤더, 페이로드를 alg에 넣고 서명값과 같은지 확인
* 같다면 유저 인가
* */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;

    /*
    * Header : HS256 암호화 방식의 JWT 명시
    * Claims : subject(토큰 제목)에 userPK 저장 -> 로그인 아이디
    * roles : 사용자 권한 저장
    * AccessToken : 5분 | RefreshToken : 7일
    * JwtTokensVO 클래스 build
    * */
    @Value("${spring.jwt.secret}")
    private String secretKey = "secretKey";
    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //복호화에서 사용할 알고리즘이 HS256이고 사용하기 위해 키가 256비트 이상이 되어야 함
    private final Long tokenValidMillisecond = 1000L*60*60; // 토큰 만료 시간 60분, 토큰이 무한정으로 사용되면X

    // 객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct // 해당 객체가 빈 객체로 주입된 이후 수행되는 메서드를 가리킴
    protected void init()
    {

        logger.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        secretKey = Encoders.BASE64.encode(key.getEncoded()); //secretkey를 Base64로 인코딩
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)); //22년 이후 BASE64에서 HMAC 사용을 권장
        logger.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }


    public String createToken(String userUid, List<String> roles)
    {
        logger.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(userUid); //토큰의 키가 되는 Subject를 중복되지 않는 고유한 값으로 지정, 토큰 복호화, JWT payLoad에 저장되는 정보단위
        claims.put("roles",roles); // 정보는 key, value 쌍으로 저장
        Date now = new Date();
        //token 빌더
        String token = Jwts.builder() // JwtBuilder객체를 생성하고 Jwts.builder()메서드를 이용 -> 토큰 생성
                .setClaims(claims) //데이터 -> 정보 저장
                .setIssuedAt(now) // 토큰 발행 일자
                .setExpiration(new Date(now.getTime()+ tokenValidMillisecond)) //만료 기간
                .signWith(key) //JWT를 서명하기 위해 사용 -> secret 값
                .compact(); // 토큰 생성
        logger.info("createToken 토큰 생성 완료");
        return token;
    }

    // JWT 토큰에서 인증 정보 조회, 토큰으로부터 클레임을 만들고, 이를 통해 User 객체를 생성하여 Authentication 객체 반환
    public Authentication getAuthentication(String token) //토큰으로 인증 객체를 얻기 위한 메소드 => 필터에서 인증을 성공했을 때 SecurityContextHolder에 저장할 Authentication을 생성하는 역할
    {
        logger.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token)); //UserDetails 타입으로 getUsername 메소드로 유저임을 확인하여 User Session을 생성
        //실제 DB에 저장되어 있는 회원 객체를 끌고와 인증처리를 진행
        logger.info("[getAuthentication] 토큰 인증 정보 조회 완료 UserDetails UserName : { }",
                userDetails.getUsername());

        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities()); //인증용 객체 ->  UsernamePasswordAuthenticationToken은 추후 인증이 끝나고 SecurityContextHolder.getContext()에 등록될 Authentication 객체이다.

    }

    // 토큰에서 회원 정보 추출
    public String getUsername(String token) // Id를 얻기위해 실제로 토큰을 디코딩
    {
        logger.info("[getUsername] 토큰 기반 회원 구별 정보 추출");


        String info = Jwts.parserBuilder() // parse : 많은 정보 중에서 원하는 데이터 조각들을 뽑아서 하나의 데이터로 만드는 것
                .setSigningKey(key)// 서명에 사용된 Secret Key를 설정하는 메서드
                .build()
                .parseClaimsJws(token) // jwt를 파싱해서 claims를 얻는 메서드 -> 검증을 위한 메서드 임으로 리턴값이 별도로 필요하지x, 파라미터는 Signature를 포함한 jwt라는 뜻
                .getBody()//객체 또는 json타입 리턴
                .getSubject(); // 지정된 key를 통해 서명된 JWT를 해석하여 Subject를 끌고와 리턴하여 이를 통해 인증 객체 끌고옴, key로 서명, 토큰 value로 claims의 subject 값(저장해둔 로그인 아이디) return

        logger.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}",info);
        return info;
    }

    // Request의 Header에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request) // 토큰을 HTTP 헤더에 저장되어 계속적으로 이동 - 토큰을 사용하기 위해 실제로 Header에서 꺼내오는 메소드
    {
        logger.info("[resolveToken] HTTP 헤더에서 Token 값 추출");


        return request.getHeader("X-AUTH-TOKEN");

    }

    // 토큰 유효성, 만료일자 확인
    public boolean validateToken(String token)
    {
        logger.info("[validateToken] 토큰 유효 체크 시작");
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token); // 토큰을 디코딩하여 만료시간을 끌고와 현재시간과 비교해 확인

            return !claims.getBody().getExpiration().before(new Date());// getExpiration()이 현재 시각보다 이전이면 예외를 던짐 => 현재 시각보다 만료가 먼저됐을 경우에 예외를 발생시킴
        }catch (Exception e){
            logger.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }
    }


}

/*
* 1. validateToken() 함수 호출
* 2. getUserId() 함수로 token claim subject 값 얻은 뒤, 회원 조회
* 3. createToken() 함수로 accessToken
 */

