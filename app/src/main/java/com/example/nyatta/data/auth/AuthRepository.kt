package com.example.nyatta.data.auth

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.nyatta.SignUpMutation
import com.example.nyatta.data.daos.UserDao
import com.example.nyatta.data.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUser(user: User)
    fun getUser(): Flow<List<User>>
    suspend fun signUp(phone: String): ApolloResponse<SignUpMutation.Data>
}

class OfflineAuthRepository(
    private val userDao: UserDao,
    private val client: ApolloClient
): AuthRepository {
    override suspend fun signUser(user: User) = userDao.insert(user)
    override fun getUser(): Flow<List<User>> = userDao.getUser()
    override suspend fun signUp(phone: String): ApolloResponse<SignUpMutation.Data> {
        return client.mutation(SignUpMutation(phone = phone)).execute()
    }
}