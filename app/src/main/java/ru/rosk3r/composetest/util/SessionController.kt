package ru.rosk3r.composetest.util

import kotlinx.coroutines.flow.Flow
import ru.rosk3r.composetest.domain.model.Session

fun isSessionExist(database: GoalGetterDatabase): Flow<List<Session>> {
    return database.sessionDao().getAll()
}
