package com.yeongjin.kopring.domain.payment.service

import com.yeongjin.kopring.domain.member.repository.MemberRepository
import com.yeongjin.kopring.domain.payment.dto.request.CreatePaymentReq
import com.yeongjin.kopring.domain.payment.dto.request.ReadPaymentsReq
import com.yeongjin.kopring.domain.payment.dto.response.ReadPaymentsRes
import com.yeongjin.kopring.domain.payment.entity.Payment
import com.yeongjin.kopring.domain.payment.repository.PaymentRepository
import com.yeongjin.kopring.global.dto.ApiResult
import com.yeongjin.kopring.global.exception.CustomException
import com.yeongjin.kopring.global.exception.ExceptionContent
import com.yeongjin.kopring.global.exception.ExceptionContent.NOT_FOUND_MEMBER
import com.yeongjin.kopring.global.utils.SecurityContextUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun createPayment(
        req: CreatePaymentReq
    ) {
        val member = memberRepository.findByIdOrNull(SecurityContextUtils.memberId)
            ?: throw CustomException(NOT_FOUND_MEMBER)
        val payment = Payment.of(req.price, member)

        paymentRepository.save(payment)
    }

    fun readPayments(
        req: ReadPaymentsReq
    ): ApiResult<ReadPaymentsRes> {
        val nextMonth = if (req.month == 12) 1 else req.month + 1
        val payments = paymentRepository.findByMemberIdAndCreatedAtBetween(
            memberId = SecurityContextUtils.memberId,
            start = LocalDateTime.of(req.year, req.month, 1, 0, 0),
            end = LocalDateTime.of(req.year, nextMonth, 1, 0, 0)
        )

        return ApiResult.ok(ReadPaymentsRes.of(payments))
    }
}