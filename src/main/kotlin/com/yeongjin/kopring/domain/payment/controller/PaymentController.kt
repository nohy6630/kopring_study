package com.yeongjin.kopring.domain.payment.controller

import com.yeongjin.kopring.domain.payment.dto.request.CreatePaymentReq
import com.yeongjin.kopring.domain.payment.dto.request.ReadPaymentsReq
import com.yeongjin.kopring.domain.payment.dto.response.ReadPaymentsRes
import com.yeongjin.kopring.domain.payment.service.PaymentService
import com.yeongjin.kopring.global.dto.ApiResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/payments")
class PaymentController(
    private val paymentService: PaymentService
) {
    @PostMapping
    fun createPayment(
        @RequestBody req: CreatePaymentReq
    ): ResponseEntity<ApiResult<Unit>> {
        paymentService.createPayment(req)
        return ApiResult.ok<Unit>()
            .toResponseEntity()
    }

    @GetMapping
    fun readPayments(
        @RequestBody req: ReadPaymentsReq
    ): ResponseEntity<ApiResult<ReadPaymentsRes>> {
        return paymentService.readPayments(req)
            .toResponseEntity()
    }
}