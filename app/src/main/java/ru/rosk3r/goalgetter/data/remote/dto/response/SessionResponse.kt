package ru.rosk3r.goalgetter.data.remote.dto.response

import java.time.LocalDateTime

data class SessionResponse(
    val id: Long,
    val userId: Long,
    val token: String,
    val expiredDate: LocalDateTime
)
