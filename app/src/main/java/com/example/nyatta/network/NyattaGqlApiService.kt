package com.example.nyatta.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.nyatta.SignUpMutation

interface NyattaGqlApiService {
    suspend fun signUp(phone: String): ApolloResponse<SignUpMutation.Data>
}

class NyattaGqlApiRepository(
    private val apolloClient: ApolloClient
): NyattaGqlApiService {
    override suspend fun signUp(phone: String): ApolloResponse<SignUpMutation.Data> {
        return apolloClient
            .mutation(SignUpMutation(phone = phone))
            .execute()
    }
}