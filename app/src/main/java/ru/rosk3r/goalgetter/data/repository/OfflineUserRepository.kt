package ru.rosk3r.goalgetter.data.repository

import kotlinx.coroutines.flow.Flow
import ru.rosk3r.goalgetter.data.local.UserDao
import ru.rosk3r.goalgetter.domain.model.User

class OfflineUserRepository(private val userDao: UserDao) : UserRepository {

    override fun getAllUserStream(): Flow<List<User>> = userDao.getAll()

    override suspend fun deleteUser(user: User) = userDao.delete(user)

    override fun getUserStream(id: Long): Flow<User?> = userDao.getById(id)

    override suspend fun insertUser(user: User) = userDao.insert(user)

    override suspend fun updateUser(user: User) = userDao.update(user)

}