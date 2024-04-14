package ru.rosk3r.composetest.domain.model

import java.time.LocalDate

data class Task(
    val title: String,
    val isCompleted: Boolean,
    val createdAt: LocalDate,
)
