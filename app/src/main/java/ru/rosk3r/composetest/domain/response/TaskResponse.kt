package ru.rosk3r.composetest.domain.response

import java.time.LocalDateTime

data class TaskResponse(
    val id: Long,
    val title: String,
    val isCompleted: Boolean,
    val createdAt: LocalDateTime,
)
