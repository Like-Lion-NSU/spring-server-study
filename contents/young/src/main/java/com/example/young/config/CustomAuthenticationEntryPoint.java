package com.example.young.config;

import com.example.young.dto.EntryPointErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {   // 인증이 실패한 상황 처리

    private final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException ex) throws IOException{
        /* ObjectMapper Jackson 라이브러리 중 1개
           Java 개체를 JSON으로 직렬화하거나 JSON 문자열을 Java 개체로 역직렬화*/
        ObjectMapper objectMapper = new ObjectMapper();     // ObjectMapper 객체 생성
        LOGGER.info("[commence] 인증 실패로 response.sendError 발생");

        EntryPointErrorResponse entryPointErrorResponse = new EntryPointErrorResponse();    // EntryPointErrorResponse 객체 생성
        entryPointErrorResponse.setMsg("인증이 실패하였습니다.");     // 인증 실패 문구 출력

        // 응답값 설정
        response.setStatus(401);
        response.setContentType("apllication/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(entryPointErrorResponse));
    }
}
