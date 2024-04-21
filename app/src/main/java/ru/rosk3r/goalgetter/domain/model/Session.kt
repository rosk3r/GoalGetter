package ru.rosk3r.goalgetter.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_sessions")
data class Session(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "token")
    val token: String,
)