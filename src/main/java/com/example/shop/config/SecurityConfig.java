package com.example.shop.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.shop.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.formLogin(f->f.loginPage("/members/login")
            .defaultSuccessUrl("/")
            .usernameParameter("email")
            .failureUrl("/members/login/error")
        ).logout((logoutConfig) ->logoutConfig.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")).logoutSuccessUrl("/"))
        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/","/members/**","/item/**","images/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        }).exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http.formLogin()
    //         .loginPage("/members/login")
    //         .defaultSuccessUrl("/")
    //         .usernameParameter("email")
    //         .failureUrl("/members/login/error")
    //         .and()
    //         .logout()
    //         .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
    //         .logoutSuccessUrl("/")
    //     ;
    //
    //     http.authorizeRequests()
    //         .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
    //         .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
    //         .requestMatchers("/admin/**").hasRole("ADMIN")
    //         .anyRequest().authenticated()
    //     ;
    //
    //
    //     return http.build();
    // }


}