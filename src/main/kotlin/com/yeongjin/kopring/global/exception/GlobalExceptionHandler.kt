package com.yeongjin.kopring.global.exception

import com.yeongjin.kopring.global.dto.ApiResult
import com.yeongjin.kopring.global.exception.ExceptionContent.UNKNOWN_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun handleCustomException(
        exception: CustomException
    ): ResponseEntity<ApiResult<Unit>> {
        return ApiResult.error<Unit>(exception.content)
            .toResponseEntity()
    }

    @ExceptionHandler
    fun handleException(
        exception: Exception
    ): ResponseEntity<ApiResult<Unit>> {
        return ApiResult.error<Unit>(UNKNOWN_ERROR)
            .toResponseEntity()
    }
}