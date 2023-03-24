package com.example.hana.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;
@RequiredArgsConstructor
@Slf4j
//jWT토큰으로 인증하고  SecurityContextHolder에 추가하는 필터를 설정

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    Logger logger = LoggerFactory.getLogger(this.getClass());

//    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider){
//        this.jwtTokenProvider=jwtTokenProvider;
//    } @RequiredArgsConstructor가 생성자 주입

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain)
        throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(servletRequest); //헤더에서 JWT를 받아옴

        //token null값 오류
        logger.info("[doFilterInternal] token 값 추출 완료. token : {}" , token);

        logger.info("[doFilterInternal] token 값 유효성 체크 시작");


        if (token != null && jwtTokenProvider.validateToken(token)) { //유효한 토큰인지 확인
            Authentication authentication = jwtTokenProvider.getAuthentication(token); //토큰이 유효하면 유저 정보를 받아옴
            SecurityContextHolder.getContext().setAuthentication(authentication);//SecurityContext에 Authentication 객체 저장
           logger.info("[doFilterInternal] token 값 유효성 체크 완료");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
