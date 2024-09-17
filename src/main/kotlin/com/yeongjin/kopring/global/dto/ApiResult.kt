package com.yeongjin.kopring.global.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.yeongjin.kopring.global.exception.ExceptionContent
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ApiResult<T>(
    @JsonIgnore
    val httpStatus: HttpStatus,
    val code: Int,
    val message: String,
    val data: T?
) {
    companion object {
        fun <T> ok(
            data: T? = null
        ): ApiResult<T> {
            return ApiResult(
                HttpStatus.OK,
                1000,
                "API가 성공적으로 호출됬습니다.",
                data
            )
        }

        fun <T> error(
            content: ExceptionContent
        ): ApiResult<T> {
            return ApiResult(
                content.httpStatus,
                content.code,
                content.message,
                null
            )
        }
    }

    fun toResponseEntity(): ResponseEntity<ApiResult<T>> {
        return ResponseEntity
            .status(httpStatus)
            .body(this)
    }
}