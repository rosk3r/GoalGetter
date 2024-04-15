package ru.rosk3r.composetest.data.remote.repository

import kotlinx.coroutines.flow.Flow
import ru.rosk3r.composetest.data.local.TaskDao
import ru.rosk3r.composetest.domain.model.Task

class OfflineTaskRepository(private val taskDao: TaskDao): TaskRepository {

    override fun getAllTaskStream(): Flow<List<Task>> = taskDao.getAll()


    override suspend fun deleteTask(task: Task) = taskDao.delete(task)

    override fun getTaskStream(id: Long): Flow<Task?> = taskDao.getById(id)

    override suspend fun insertTask(task: Task) = taskDao.insert(task)

    override suspend fun updateTask(task: Task) = taskDao.update(task)

}