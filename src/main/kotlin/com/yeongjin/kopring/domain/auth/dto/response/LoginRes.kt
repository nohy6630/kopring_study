package com.yeongjin.kopring.domain.auth.dto.response

import com.yeongjin.kopring.domain.auth.utils.JwtUtils
import com.yeongjin.kopring.domain.member.entity.Member

data class LoginRes(
    val accessToken: String,
) {
    companion object {
        fun of(
            member: Member,
            secretKey: String,
            tokenExpire: Long,
        ): LoginRes {
            val token = JwtUtils
                .createToken(member, secretKey, tokenExpire)

            return LoginRes(token)
        }
    }
}