package com.example.nyatta.data.auth

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.nyatta.CreateUserMutation
import com.example.nyatta.model.AuthRequest

interface AuthRepository {
    suspend fun signUser(request: AuthRequest): ApolloResponse<CreateUserMutation.Data>
}

class GqlAuthRepository(
    private val client: ApolloClient
): AuthRepository {
    override suspend fun signUser(
        request: AuthRequest
    ): ApolloResponse<CreateUserMutation.Data> {
        return client.mutation(
            CreateUserMutation(
                first_name = request.firstname,
                last_name = request.lastname,
                email = request.email,
                phone = request.phone
            )
        ).execute()
    }
}