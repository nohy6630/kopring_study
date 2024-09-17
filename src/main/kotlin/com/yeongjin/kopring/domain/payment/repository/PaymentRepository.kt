package com.yeongjin.kopring.domain.payment.repository

import com.yeongjin.kopring.domain.member.entity.Member
import com.yeongjin.kopring.domain.payment.entity.Payment
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

interface PaymentRepository : CrudRepository<Payment, Long> {
    fun findByMemberIdAndCreatedAtBetween(
        memberId: Long,
        start: LocalDateTime,
        end: LocalDateTime
    ): List<Payment>
}