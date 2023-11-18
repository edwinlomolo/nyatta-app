package com.example.nyatta.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.nyatta.CreatePaymentMutation
import com.example.nyatta.SignInMutation

interface NyattaGqlApiService {
    suspend fun signIn(phone: String): ApolloResponse<SignInMutation.Data>

    suspend fun createPayment(phone: String, amount: String): ApolloResponse<CreatePaymentMutation.Data>
}

class NyattaGqlApiRepository(
    private val apolloClient: ApolloClient
): NyattaGqlApiService {
    override suspend fun signIn(phone: String): ApolloResponse<SignInMutation.Data> {
        return apolloClient
            .mutation(SignInMutation(phone = phone))
            .execute()
    }

    override suspend fun createPayment(phone: String, amount: String): ApolloResponse<CreatePaymentMutation.Data> {
        return apolloClient
            .mutation(CreatePaymentMutation(phone = phone, amount = amount))
            .execute()
    }
}