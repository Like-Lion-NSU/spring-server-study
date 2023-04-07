package com.springboot.todo.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//Spring Security에서 인증되었으나 권한이 없는 사용자의 리소스에 대한 접근 처리
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler { //액세스 권한이 없는 리소스에 접근할 경우 발생하는 예외(리다이렉트 방식)
    private final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exception) throws IOException {
        LOGGER.info("[handle] 접근이 막혔을 경우 경로 리다이렉트");
        response.sendRedirect("/exception");
    }
}

