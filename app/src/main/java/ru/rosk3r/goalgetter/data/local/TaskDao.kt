package ru.rosk3r.goalgetter.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.rosk3r.goalgetter.domain.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM t_tasks")
    fun getAll(): Flow<List<Task>>

    @Query("SELECT * FROM t_tasks WHERE id = :id")
    fun getById(id: Long): Flow<Task>

    @Insert()
    fun insert(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

}