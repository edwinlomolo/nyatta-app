package com.example.nyatta.data.auth

import com.apollographql.apollo3.api.ApolloResponse
import com.example.nyatta.SignUpMutation
import com.example.nyatta.data.daos.UserDao
import com.example.nyatta.data.model.User
import com.example.nyatta.network.NyattaGqlApiRepository
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUser(user: User)
    fun getUser(): Flow<List<User>>
    suspend fun signUp(phone: String): ApolloResponse<SignUpMutation.Data>
}

class OfflineAuthRepository(
    private val userDao: UserDao,
    private val nyattaGqlApiRepository: NyattaGqlApiRepository
): AuthRepository {

    override suspend fun signUser(user: User) = userDao.insert(user)

    override fun getUser(): Flow<List<User>> = userDao.getUser()

    override suspend fun signUp(phone: String): ApolloResponse<SignUpMutation.Data> {
        return nyattaGqlApiRepository.signUp(phone)
    }
}