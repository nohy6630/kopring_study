package com.yeongjin.kopring.global.exception

import com.yeongjin.kopring.global.dto.ApiResult
import com.yeongjin.kopring.global.exception.ExceptionContent.*
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.ErrorResponse
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException


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
    fun handleJsonException(
        exception: HttpMessageNotReadableException
    ): ResponseEntity<ApiResult<Unit>> {
        return ApiResult.error<Unit>(INVALID_JSON_FORMAT)
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