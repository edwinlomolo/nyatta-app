package com.example.nyatta.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.nyatta.HelloQuery

interface HelloRepository {
    suspend fun getHello(): ApolloResponse<HelloQuery.Data>
}
class ApolloHelloRepository(
    private val apolloClient: ApolloClient
): HelloRepository {
    override suspend fun getHello(): ApolloResponse<HelloQuery.Data> {
        return apolloClient.query(HelloQuery()).execute()
    }
}