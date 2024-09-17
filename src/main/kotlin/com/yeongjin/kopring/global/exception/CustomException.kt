package com.yeongjin.kopring.global.exception

class CustomException(
    val content: ExceptionContent
) : RuntimeException()