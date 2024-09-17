package com.yeongjin.kopring.domain.auth.dto.request

data class LoginReq (
    val email: String,
    val password: String,
)