package com.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/" , "/login" , "/loginProc" , "/join" , "/joinProc").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/my/**").hasAnyRole("ADMIN" ,"USER")
                .anyRequest().authenticated() // 로그인을 한 사용자만 접근이 가능함
        );

//        http
//                .httpBasic(Customizer.withDefaults());

        http
                .formLogin((auth)-> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );
        // 원래는 csfr를 보내줘야 인증이 가능한데 개발환경이기 때문에 auth.disable을 작동시킨다.

//        http
//                .csrf((auth) -> auth.disable());

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());
        http
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }

/*
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.builder()
                .username("user1")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("user2")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

 */

}
