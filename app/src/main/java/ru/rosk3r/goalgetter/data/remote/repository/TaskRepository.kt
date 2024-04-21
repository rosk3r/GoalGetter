package ru.rosk3r.goalgetter.data.remote.repository

import kotlinx.coroutines.flow.Flow
import ru.rosk3r.goalgetter.domain.model.Task

interface TaskRepository {

    fun getAllTaskStream(): Flow<List<Task>>
    fun getTaskStream(id: Long): Flow<Task?>
    suspend fun insertTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun deleteAll()
    suspend fun updateTask(task: Task)

}