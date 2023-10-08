package com.example.nyatta.data.towns

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.nyatta.GetTownsQuery

interface TownsRepository {
    suspend fun getTowns(): ApolloResponse<GetTownsQuery.Data>
}

class GqlTownsRepository(
    private val client: ApolloClient
): TownsRepository {
    override suspend fun getTowns(): ApolloResponse<GetTownsQuery.Data> {
        return client.query(GetTownsQuery()).execute()
    }
}