package com.yeongjin.kopring.domain.payment.dto.response

import com.yeongjin.kopring.domain.payment.entity.Payment

data class ReadPaymentsRes(
    val size: Int,
    val data: List<PaymentRes>,
) {
    companion object {
        fun of(
            payments: List<Payment>
        ): ReadPaymentsRes {
            val size = payments.size
            val data = payments.map { payment ->
                PaymentRes.of(payment)
            }

            return ReadPaymentsRes(
                size = size,
                data = data
            )
        }
    }
}