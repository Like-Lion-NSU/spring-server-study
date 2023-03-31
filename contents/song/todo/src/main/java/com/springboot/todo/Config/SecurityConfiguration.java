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
@EnableWebSecurity(debug = true) //Spring Security 설정 활성화 -> 스프링 시큐리티 필터가 스프링 필터체인에 등록 됨
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    //의존성 주입
    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //암호화에 필요한 PasswordEncoder를 Bean 등록
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ //필터는 요청정보 즉 클라이언트가 요청하는 정보를 변경할 수 있는 서블릿 컨테이너 두번째 필터는 클라이언트의 요청하는 정보가 아닌 첫번째 필터에 의해서 변경된 요청 정보를 변경하게 됨
        //mySql 콘솔 사용
        http.httpBasic().disable()//ui를 사용하는 것을 기본값으로 가진 시큐리티 설정을 비활성화함
                .csrf().disable()//REST API에서는 CSRF 보안이 필요 없기 때문에 비활성화하는 로직 -> csrf 사용 안 함 == REST API 사용하기 때문
                //세션 사용 안함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // REST API 기반 애플리케이션 동작 방식 설정, 현재 JWT 토큰으로 인증을 처리하고, 세션은 사용하지 않기 때문에 STATELESS로 설정
                //URL 관리
                .and()
                .authorizeRequests()// 애플리케이션에 들어오는 요청에 대한 사용 권한 체크 => 다음 리퀘스트에 대한 사용권한 체크
                .antMatchers("/sign-in", "/sign-up", "/exception").permitAll()// antPattern을 통해 권한을 설정하는 역할 => 누구나 접근가능
                .and()
                // JwtAuthenticationFilter를 먼저 적용
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class); //UsernamePasswordAuthenticationFilter 전에 JwtAuthenticationFilter 삽입/실행 하겠다


        return http.build();
    }
    @Bean //Bean 등록
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) ->web.ignoring().antMatchers("/css/**","/js/**","/img/**","script/**","/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**","/v2/api-docs");
    }
}

/*
- http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    -> http csrf 기능을 사용하지 않고, jwt 토큰을 사용할 것이기 때문에 sessionManagement의 세션 정책에서 STATELASS를 설정

- .antMatchers("/user/{id}").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") .anyRequest().permitAll()
    -> 어떤 URL 주소를 어떤 권한을 가진 사람에게 허용할 것인지 설정하는 페이지

- .and().httpBasic().disable().formLogin().disable().addFilterBefore(newJwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class).addFilterBefore(new JwtExceptionFilter(objectMapper), JwtAuthenticationFilter.class);
    -> JWT 토큰을 사용하려면 httpBasic과 formLogin을 disable 해야함. 두 개는 ID-PW를 이용하는 기본적인 방법
    -> JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter.class 필터 전에 필터체인을 탈 수 있게 addFilterBefore를 하고 예외 처리를 위한 JwtExceptionFilter를 위에 등록한 JwtAuthenticationFilter 전에 등록
- csrf(cross site request forgery) : 사이트간 위조 요청, 인증된 사용자의 토큰을 탈취해 위조된 요청을 보냈을 경우 파악해 방지하는 것
    => disable 이유? rest api에서는 권한이 필요한 요청 위해서 인증 정보를 포함시켜야 함. 서버에 인증정보 저장하지 않기 때문에 작성할 필요 없음(JWT를 쿠키에 저장하지 않기 때문)
* */