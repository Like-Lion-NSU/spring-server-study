package com.example.young.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private  final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // UI를 사용하는 것을 기본값으로 가진 시큐리티 설정 비활성화
        http.httpBasic().disable()
                // CSRF 보안(사이트 간 요청 위조) 비활성화
                .csrf().disable()
                /* REST API 기반 애플리케이션 동작 방식 설정
                    JWT 토큰으로 인증을 처리하여 세션은 사용하지 않기 떄문에 STATELESS로 설정*/
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)

                .and()
                // 애플리케이션에 들어오는 요청에 대한 사용 권한 체크
                .authorizeRequests()
                // ""해당 경로에 대해서는 모두 허용
                .requestMatchers("/sign-api/sign-in", "/sign-api/sign-up",
                        "/sign-api/exception", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // exception 단어가 들어간 경로 모두 허용
                .requestMatchers("**exception**").permitAll()
                // 인증된 권한(ADMIN)을 가진 사용자에게 허용
                //.anyRequest().hasRole("ADMIN")

                .and()
                // 권한을 확인하는 과정에서 통과하지 못하는 예외가 발생할 경우 예외 전달
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                // 인증 과정에서 예외가 발생할 경우 예외 전달
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                .and()
                // UsernamePasswordAuthenticationFilter 앞에 JwtAuthenticationFilter를 추가
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
