package ru.rosk3r.goalgetter.data.remote.dto.response

import java.time.LocalDateTime

data class TaskResponse(
    val id: Long,
    val title: String,
    val isCompleted: Boolean,
    val createdAt: String,
)
