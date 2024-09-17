package com.yeongjin.kopring.global.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*;

enum class ExceptionContent(
    val httpStatus: HttpStatus,
    val code: Int,
    val message: String
) {
    INVALID_PASSWORD(UNAUTHORIZED, 2003, "비밀번호가 잘못되었습니다."),
    NOT_AUTHENTICATION(UNAUTHORIZED, 2004, "인증이 필요합니다."),
    NOT_FOUND_MEMBER(BAD_REQUEST, 2006, "존재하지 않는 사용자입니다."),
    ALREADY_REGISTERED_EMAIL(BAD_REQUEST, 2007, "이미 가입된 이메일입니다."),
    UNKNOWN_ERROR(INTERNAL_SERVER_ERROR, 9999, "알 수 없는 오류가 발생했습니다.")
}