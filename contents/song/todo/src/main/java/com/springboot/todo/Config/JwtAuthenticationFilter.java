package com.springboot.todo.Config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//http 요청이 들어오면 가장 먼저 거치게 될 필터
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    //인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 탐
    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 토큰 받아오기
        String token = jwtTokenProvider.resolveToken(servletRequest); //username과 password로 토큰 생성
        LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);

        LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");
        // 토큰이 유효하다면
        if(token !=null && jwtTokenProvider.validateToken(token)){
            // 토큰으로부터 유저 정보를 받아
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext에 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
        }
        // 다음 Filter 실행, 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
        filterChain.doFilter(servletRequest, servletResponse);
    }

}