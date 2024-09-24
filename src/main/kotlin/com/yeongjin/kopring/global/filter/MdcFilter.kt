package com.yeongjin.kopring.global.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class MdcFilter
    : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        MDC.put("request_id", UUID.randomUUID().toString())
        log.info("Request: ${request.method} - ${request.requestURI}")
        filterChain.doFilter(request, response);
        log.info("Response: ${request.method} - ${request.requestURI}")
        MDC.clear()
    }
}