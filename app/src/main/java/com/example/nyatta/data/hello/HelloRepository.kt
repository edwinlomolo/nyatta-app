package com.example.nyatta.data.hello

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.nyatta.HelloQuery

interface HelloRepository {
    suspend fun getHello(): ApolloResponse<HelloQuery.Data>
}
class GqlHelloRepository(
    private val client: ApolloClient
): HelloRepository {
    override suspend fun getHello(): ApolloResponse<HelloQuery.Data> {
        return client.query(HelloQuery()).execute()
    }
}