package com.yeongjin.kopring.domain.member.repository

import com.yeongjin.kopring.domain.member.entity.Member
import org.springframework.data.repository.CrudRepository
import java.util.*

interface MemberRepository : CrudRepository<Member, Long> {
    fun findByEmail(email: String): Member?
    fun existsByEmail(email: String): Boolean
}