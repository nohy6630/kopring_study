package com.yeongjin.kopring.domain.auth.controller

import com.yeongjin.kopring.domain.auth.dto.request.LoginReq
import com.yeongjin.kopring.domain.auth.dto.request.SignupReq
import com.yeongjin.kopring.domain.auth.dto.response.LoginRes
import com.yeongjin.kopring.domain.auth.service.AuthService
import com.yeongjin.kopring.global.dto.ApiResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.lang.model.type.NullType

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(
        @RequestBody loginReq: LoginReq
    ): ResponseEntity<ApiResult<LoginRes>> {
        return authService.login(loginReq)
            .toResponseEntity()
    }

    @PostMapping("/signup")
    fun signup(
        @RequestBody signupReq: SignupReq
    ): ResponseEntity<ApiResult<Unit>> {
        authService.signup(signupReq)
        return ApiResult.ok<Unit>()
            .toResponseEntity()
    }
}