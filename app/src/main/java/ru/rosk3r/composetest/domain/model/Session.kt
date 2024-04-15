package ru.rosk3r.composetest.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "token")
    val token: String,
)