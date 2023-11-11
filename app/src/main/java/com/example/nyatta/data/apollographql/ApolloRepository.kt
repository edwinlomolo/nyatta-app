package com.example.nyatta.data.apollographql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.nyatta.HelloQuery

interface ApolloRepository {
    suspend fun getHello(jwt: String): ApolloResponse<HelloQuery.Data>
}

class ApolloGraphqlRepository(
    private val client: ApolloClient
): ApolloRepository {
    override suspend fun getHello(jwt: String): ApolloResponse<HelloQuery.Data> {
        return client.query(HelloQuery()).addHttpHeader("Authorization", "Bearer $jwt").execute()
    }
}