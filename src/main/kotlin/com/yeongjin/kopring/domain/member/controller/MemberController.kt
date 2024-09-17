package com.yeongjin.kopring.domain.member.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/members")
class MemberController {
    @GetMapping("/test")
    fun test(): String {
        return "test"
    }
}