package ru.rosk3r.composetest.data.remote.dto.response

data class SessionResponse(
    val id: Long,
    val userId: Long,
    val token: String,
    val expiredDate: String
)
