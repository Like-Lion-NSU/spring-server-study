package com.example.hana.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

//액세스 권한이 없는 리소스에 접근할 경우 발생하는 예외처리
@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        logger.info("[hendle] 접근이 막혔을 경우 경로 리다이렉트");
        response.sendRedirect("/sign-api/exception");
    }
}
