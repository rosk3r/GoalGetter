package ru.rosk3r.composetest.data.remote.repository

import ru.rosk3r.composetest.data.local.SessionDao
import ru.rosk3r.composetest.domain.model.Session

class SessionRepository(
    private val sessionDao: SessionDao
) {

    suspend fun insert(session: Session) {
        sessionDao.insert(session)
    }

}