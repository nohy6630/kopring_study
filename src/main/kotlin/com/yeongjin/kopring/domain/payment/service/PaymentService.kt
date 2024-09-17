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
import com.yeongjin.kopring.global.redis.RedisLockRepository
import com.yeongjin.kopring.global.utils.SecurityContextUtils
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.support.AbstractApplicationContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.math.log

@Service
@Transactional(readOnly = true)
@Slf4j
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val memberRepository: MemberRepository,
    private val redisLockRepository: RedisLockRepository,
    private val applicationContext: ApplicationContext
) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun createPayment(
        req: CreatePaymentReq
    ) {
        if (!redisLockRepository.lock(SecurityContextUtils.memberId)) {
            log.warn("${SecurityContextUtils.memberId}번 유저의 결제가 중복으로 발생해 차단됨")
            throw CustomException(ExceptionContent.PAYMENT_IN_PROGRESS)
        }

        log.info("${SecurityContextUtils.memberId}번 유저의 결제 시작...")

        val member = memberRepository.findByIdOrNull(SecurityContextUtils.memberId)
            ?: throw CustomException(NOT_FOUND_MEMBER)
        Thread.sleep(1000)//실제 결제처럼 딜레이
        val payment = Payment.of(req.price, member)

        paymentRepository.save(payment)
        redisLockRepository.unlock(SecurityContextUtils.memberId)
        log.info("${SecurityContextUtils.memberId}번 유저의 결제 종료")
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