package ru.rosk3r.goalgetter.util

import kotlinx.coroutines.flow.Flow
import ru.rosk3r.goalgetter.domain.model.Session

fun isSessionExist(database: GoalGetterDatabase): Flow<List<Session>> {
    return database.sessionDao().getAll()
}
