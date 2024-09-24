package com.yeongjin.kopring.global.config

import com.yeongjin.kopring.domain.auth.filter.JwtFilter
import com.yeongjin.kopring.global.filter.MdcFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.security.web.context.SecurityContextHolderFilter

@Configuration
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val mdcFilter: MdcFilter
) {
    @Bean
    fun filterChain(
        http: HttpSecurity,
        authFailHandler: AuthenticationEntryPoint,
    ): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeRequests {
                authorize(POST, "/api/v1/auth/login", permitAll)
                authorize(POST, "/api/v1/auth/signup", permitAll)
                authorize(anyRequest, authenticated)
            }
            addFilterBefore<SecurityContextHolderFilter>(mdcFilter)
            addFilterBefore<ExceptionTranslationFilter>(jwtFilter)
            exceptionHandling {
                authenticationEntryPoint = authFailHandler
            }
            sessionManagement { sessionCreationPolicy = STATELESS }
        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { request, response, authException ->
            response.sendError(401, "Unauthorized")//todo 공통 response 형식과 통일
        }
    }
}