package com.yeongjin.kopring.domain.auth.filter

import com.yeongjin.kopring.domain.auth.utils.JwtUtils
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    @Value("\${jwt.secret-key}") private val secretKey: String,
    @Value("\${jwt.token-expire}") private val tokenExpire: Long,
) : OncePerRequestFilter() {
    companion object {
        const val PREFIX_BEARER = "Bearer "
    }

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (jwt != null) {
            try {
                val memberId = JwtUtils.getMemberIdFromToken(
                    jwt.replace(PREFIX_BEARER, ""),
                    secretKey
                )
                val auth = UsernamePasswordAuthenticationToken
                    .authenticated(memberId, null, null)
                SecurityContextHolder.getContext().authentication = auth
            } catch (e: JwtException) {
                log.warn("Invalid JWT: $jwt")
            }
        }
        filterChain.doFilter(request, response)
    }
}