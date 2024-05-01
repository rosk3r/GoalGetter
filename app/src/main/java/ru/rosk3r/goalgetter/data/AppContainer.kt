package ru.rosk3r.goalgetter.data

import android.content.Context
import ru.rosk3r.goalgetter.data.repository.OfflineSessionRepository
import ru.rosk3r.goalgetter.data.repository.OfflineTaskRepository
import ru.rosk3r.goalgetter.data.repository.OfflineUserRepository
import ru.rosk3r.goalgetter.data.repository.SessionRepository
import ru.rosk3r.goalgetter.data.repository.TaskRepository
import ru.rosk3r.goalgetter.data.repository.UserRepository
import ru.rosk3r.goalgetter.util.GoalGetterDatabase

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
