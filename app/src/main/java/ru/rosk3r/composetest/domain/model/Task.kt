package ru.rosk3r.composetest.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Task(
    val id: Long,
    val title: String,
    val isCompleted: Boolean,
    val createdAt: LocalDateTime,
)
