package ru.rosk3r.goalgetter.data.repository

import kotlinx.coroutines.flow.Flow
import ru.rosk3r.goalgetter.data.local.TaskDao
import ru.rosk3r.goalgetter.domain.model.Task

class OfflineTaskRepository(private val taskDao: TaskDao): TaskRepository {

    override fun getAllTaskStream(): Flow<List<Task>> = taskDao.getAll()

    override suspend fun deleteTask(task: Task) = taskDao.delete(task)

    override suspend fun deleteAll() = taskDao.deleteAll()

    override fun getTaskStream(id: Long): Flow<Task?> = taskDao.getById(id)

    override suspend fun insertTask(task: Task) = taskDao.insert(task)

    override suspend fun updateTask(task: Task) = taskDao.update(task)

    override suspend fun updateTitleById(id: Long, newTitle: String) = taskDao.updateTitleById(id, newTitle)

}