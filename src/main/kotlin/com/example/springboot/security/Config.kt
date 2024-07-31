package com.example.springboot.config

import com.example.springboot.interceptors.HeaderAuthenticationJwtFilter
import com.example.springboot.security.CustomAccessDeniedHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class Config {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf{ it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/login").permitAll()
                it.anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .httpBasic { it.disable() }
            .formLogin { it.disable()}
            .addFilterBefore(HeaderAuthenticationJwtFilter(), UsernamePasswordAuthenticationFilter::class.java)
//            .exceptionHandling{
//                it.authenticationEntryPoint(CustomAccessDeniedHandler())
//            }
            .build()
    }
}
