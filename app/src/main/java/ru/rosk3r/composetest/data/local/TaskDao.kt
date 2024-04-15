package ru.rosk3r.composetest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.rosk3r.composetest.domain.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task")
    fun getAll(): Flow<List<Task>>

    @Insert()
    fun insert(task: Task)

}