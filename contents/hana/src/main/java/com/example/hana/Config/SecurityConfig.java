package com.example.hana.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//스프링 시큐리티 설정
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;


    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.httpBasic().disable()//Http basic Auth 기반으로 뜨는 로그인 인증창 비활성화

                .csrf().disable()//csrf 비활성화

                //인증정보를 서버에 담아두지 않음
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()//요청에 대한 권한 체크
                .requestMatchers("/sign-in", "/sign-up",
                        "/exception", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()//exception 단어가 들어간 경로 모두 허용

                .requestMatchers("**exception**").permitAll()

//                .anyRequest().hasRole("ADMIN")

                // permitAll이 아닌 다른 요청에 대해서는 인가된게 아니면 접근 금지시킴
                .anyRequest().authenticated()
                .and()

                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()

                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers( "/swagger-resources/**",
//                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception","/v3/api-docs",
//                "/v3/api-docs/**");//스웨거와 관련된 경로 예외처리->인증,인가 무시
//    }

