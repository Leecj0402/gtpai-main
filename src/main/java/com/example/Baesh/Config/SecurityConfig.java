package com.example.Baesh.Config;

import com.example.Baesh.JWT.JWTRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    private final JWTRequestFilter jwtRequestFilter;

    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {this.jwtRequestFilter = jwtRequestFilter;}


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.cors();

        // CSRF 설정, OAuth 로그인 제외
        http.csrf().disable();
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.ignoringRequestMatchers("/v1/oauth/login/**")
                //.ignoringRequestMatchers("/**");
        // Stateless 세션 정책 설정
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // JWT 필터 설정
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // 요청에 대한 권한 설정
        http.authorizeHttpRequests()
                .requestMatchers("/v1/oauth/login").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated();  // 나머지 요청은 인증 필요
        return http.build();
    }
}
