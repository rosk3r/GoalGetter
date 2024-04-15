package ru.rosk3r.composetest.data

import android.content.Context
import ru.rosk3r.composetest.data.remote.repository.OfflineSessionRepository
import ru.rosk3r.composetest.data.remote.repository.OfflineTaskRepository
import ru.rosk3r.composetest.data.remote.repository.OfflineUserRepository
import ru.rosk3r.composetest.data.remote.repository.SessionRepository
import ru.rosk3r.composetest.data.remote.repository.TaskRepository
import ru.rosk3r.composetest.data.remote.repository.UserRepository
import ru.rosk3r.composetest.util.GoalGetterDatabase

interface AppContainer {
    val taskRepository: TaskRepository
    val sessionRepository: SessionRepository
    val userRepository: UserRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val taskRepository: TaskRepository by lazy {
        OfflineTaskRepository(GoalGetterDatabase.getDatabase(context).taskDao())
    }

    override val sessionRepository: SessionRepository by lazy {
        OfflineSessionRepository(GoalGetterDatabase.getDatabase(context).sessionDao())
    }

    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(GoalGetterDatabase.getDatabase(context).userDao())
    }
}
