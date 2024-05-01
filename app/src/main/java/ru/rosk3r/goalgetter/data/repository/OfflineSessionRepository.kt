package ru.rosk3r.goalgetter.data.repository

import kotlinx.coroutines.flow.Flow
import ru.rosk3r.goalgetter.data.local.SessionDao
import ru.rosk3r.goalgetter.domain.model.Session

class OfflineSessionRepository(private val sessionDao: SessionDao): SessionRepository {

    override fun getAllSessionStream(): Flow<List<Session>> = sessionDao.getAll()

    override suspend fun deleteSession(session: Session) = sessionDao.delete(session)

    override fun hasAnyRecords(): Boolean = sessionDao.hasAnyRecords()

    override fun getOne(): Session = sessionDao.getOne()

    override fun getSessionStream(id: Long): Flow<Session?> = sessionDao.getById(id)

    override suspend fun insertSession(session: Session) = sessionDao.insert(session)

    override suspend fun updateSession(session: Session) = sessionDao.update(session)

}