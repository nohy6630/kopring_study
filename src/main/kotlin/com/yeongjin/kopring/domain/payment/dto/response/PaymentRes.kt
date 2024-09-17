package com.yeongjin.kopring.domain.payment.dto.response

import com.yeongjin.kopring.domain.payment.entity.Payment
import java.time.LocalDateTime

data class PaymentRes(
    val paymentTime: LocalDateTime,
    val price: Long,
) {
    companion object {
        fun of(
            payment: Payment
        ): PaymentRes {
            return PaymentRes(
                paymentTime = payment.createdAt,
                price = payment.price,
            )
        }
    }
}