package com.yeongjin.kopring.global.utils

import org.springframework.security.core.context.SecurityContextHolder


object SecurityContextUtils {
    val memberId: Long
        get() {
            val auth = SecurityContextHolder.getContext().authentication
            return auth.principal as Long
        }
}
