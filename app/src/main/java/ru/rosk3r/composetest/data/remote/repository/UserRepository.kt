package ru.rosk3r.composetest.data.remote.repository

import kotlinx.coroutines.flow.Flow
import ru.rosk3r.composetest.domain.model.User

interface UserRepository {

    fun getAllUserStream(): Flow<List<User>>
    fun getUserStream(id: Long): Flow<User?>
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun updateUser(user: User)

}