package com.example.hana.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


//jWT 토큰으로 인증하고  SecurityContextHolder에 추가하는 필터를 설정
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    Logger logger = LoggerFactory.getLogger(this.getClass());

//    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider){
//        this.jwtTokenProvider=jwtTokenProvider;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
                                    HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(servletRequest);

        if (token != null) {
            token = token.substring(7);
        }
        logger.info("[doFilterInternal] token 값 추출 완료. token : {}", token);
        logger.info("[doFilterInternal] token 값 유효성 체크 시작");

       //토큰이 유효하면 SecurityContextHolder에 Authentication 추가
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("[doFilterInternal] token 값 유효성 체크 완료");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}