package ru.rosk3r.goalgetter.data.repository

import kotlinx.coroutines.flow.Flow
import ru.rosk3r.goalgetter.domain.model.User

interface UserRepository {

    fun getAllUserStream(): Flow<List<User>>

    fun getUserStream(id: Long): Flow<User?>

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun updateUser(user: User)

}