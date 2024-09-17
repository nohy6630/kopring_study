package com.yeongjin.kopring.domain.payment.entity

import com.yeongjin.kopring.domain.member.entity.Member
import com.yeongjin.kopring.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
class Payment(
    val price: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
): BaseEntity(){
    companion object {
        fun of(
            price: Long,
            member: Member
        ): Payment {
            return Payment(
                price = price,
                member = member
            )
        }
    }
}