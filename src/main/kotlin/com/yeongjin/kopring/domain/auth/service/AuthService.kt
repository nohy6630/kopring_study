package com.yeongjin.kopring.domain.auth.service

import com.yeongjin.kopring.domain.auth.dto.request.LoginReq
import com.yeongjin.kopring.domain.auth.dto.request.SignupReq
import com.yeongjin.kopring.domain.auth.dto.response.LoginRes
import com.yeongjin.kopring.domain.member.entity.Member
import com.yeongjin.kopring.domain.member.repository.MemberRepository
import com.yeongjin.kopring.global.dto.ApiResult
import com.yeongjin.kopring.global.exception.CustomException
import com.yeongjin.kopring.global.exception.ExceptionContent
import com.yeongjin.kopring.global.exception.ExceptionContent.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${jwt.secret-key}") private val secretKey: String,
    @Value("\${jwt.token-expire}") private val tokenExpire: Long,
) {
    fun login(
        loginReq: LoginReq
    ): ApiResult<LoginRes> {
        val member = memberRepository.findByEmail(loginReq.email)
            ?: throw CustomException(NOT_FOUND_MEMBER)
        if (!passwordEncoder.matches(loginReq.password, member.password))
            throw CustomException(INVALID_PASSWORD)

        return ApiResult.ok(LoginRes.of(member, secretKey, tokenExpire))
    }

    @Transactional
    fun signup(
        signupReq: SignupReq
    ) {
        if (memberRepository.existsByEmail(signupReq.email))
            throw CustomException(ALREADY_REGISTERED_EMAIL)

        val member = Member.of(
            signupReq.email,
            passwordEncoder.encode(signupReq.password)
        )
        memberRepository.save(member)
    }
}