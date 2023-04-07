package com.example.young.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor    // final 또는 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // OnecePerRequsetFilter는 필터를 상속 받아 사용하는 대표적인 객체 중 1개
    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;


    @Override       // OncePerRequsetFilter로부터 Overriding
    protected void doFilterInternal(HttpServletRequest servletRequest,
                                    HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(servletRequest);
        if(token !=null) {      // 토큰 값이 널이 아니면 Bearer 삭제
            token = token.substring(7);
        }
        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);
        LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");
        if(token != null && jwtTokenProvider.validateToken(token)){ // 토큰이 널 값이 아니고 유효하다면
            Authentication authentication = jwtTokenProvider.getAuthentication(token); // authentication 객체 생성
            SecurityContextHolder.getContext().setAuthentication(authentication);  //  SecurityContextHolder에 추가
            LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
        }
        /* doFilter를 기준으로 앞에 작성한 코드는 서블릿이 실행 되기 전 실행
        *  뒤에 작성한 코드는 서블릿이 실행된 후 실행*/
        filterChain.doFilter(servletRequest, servletResponse);      // 서블릿 실행
    }
}
