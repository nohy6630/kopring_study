package com.yeongjin.kopring.domain.member.entity

import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(
    @Column(unique = true)
    val email: String,

    val password: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    companion object {
        fun of(
            email: String,
            password: String,
        ): Member {
            return Member(email, password)
        }
    }
}