package ru.rosk3r.goalgetter.data.remote.dto.response

data class UserStatsResponse (
    val username: String,
    val completedTasksCount: Long,
    val rank: Long,
)