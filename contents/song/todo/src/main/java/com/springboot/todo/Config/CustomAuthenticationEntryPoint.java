package com.springboot.todo.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.todo.Dto.EntryPointErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//AuthenticationEntryPoint : Spring Security에서 인증되지 않은 사용자의 리소스에 대한 접근 처리 담당
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint { // 인증 실패했을때 예외처리 - Response를 생성해서 클라이언트에게 응답하는 방식
    private final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException ex) throws IOException {
        //ObjectMapper : JSON 컨텐츠를 Java 객체로 deserialization하거나 Java 객체를 Json으로 serialization 할때 사용하는 Jackson 라이브러리의 클래스
        ObjectMapper objectMapper = new ObjectMapper();

        LOGGER.info("[commence] 인증 실패로 response.sendError 발생");

        EntryPointErrorResponse entryPointErrorResponse = new EntryPointErrorResponse();
        entryPointErrorResponse.setMsg("인증이 실패하였습니다.");

        response.setStatus(401);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(entryPointErrorResponse));
    }
}
