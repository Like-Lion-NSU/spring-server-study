package com.springboot.todo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true) ////Spring Security 설정 활성화
public class SecurityConfiguration {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ //필터는 요청정보 즉 클라이언트가 요청하는 정보를 변경할 수 있는 서블릿 컨테이너 두번째 필터는 클라이언트의 요청하는 정보가 아닌 첫번째 필터에 의해서 변경된 요청 정보를 변경하게 됨
        //mySql 콘솔 사용
        http.httpBasic().disable().csrf().disable()
                //세션 사용 안함
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
                //URL 관리
                .and()
                .authorizeRequests()
                .antMatchers("/sign-in", "/sign-up",
                        "/exception").permitAll()
                .and()
                // JwtAuthenticationFilter를 먼저 적용
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) ->web.ignoring().antMatchers("/css/**","/js/**","/img/**","script/**","/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**","/v2/api-docs");
    }
}

