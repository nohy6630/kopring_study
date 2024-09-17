package com.yeongjin.kopring.domain.auth.utils

import com.yeongjin.kopring.domain.member.entity.Member
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys
import java.util.*
import kotlin.text.Charsets.UTF_8

class JwtUtils {
    companion object {
        const val CLAIM_ID = "id"

        fun createToken(
            member: Member,
            secretKey: String,
            tokenExpire: Long,
        ): String {
            return Jwts.builder()
                .claim(CLAIM_ID, member.id)
                .expiration(Date(System.currentTimeMillis() + tokenExpire))
                .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray(UTF_8)))
                .compact()
        }

        fun getMemberIdFromToken(
            token: String,
            secretKey: String,
        ): Long {
            val claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray(UTF_8)))
                .build()
                .parseSignedClaims(token)
                .payload

            return claims.get(CLAIM_ID, Integer::class.java).toLong()
        }
    }
}