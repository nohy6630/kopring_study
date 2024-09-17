package com.yeongjin.kopring.domain.member.entity

import com.yeongjin.kopring.domain.payment.entity.Payment
import com.yeongjin.kopring.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
class Member(
    @Column(unique = true)
    val email: String,

    val password: String,

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL])
    val payments: List<Payment> = emptyList(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseEntity() {
    companion object {
        fun of(
            email: String,
            password: String,
        ): Member {
            return Member(
                email = email,
                password = password
            )
        }
    }
}