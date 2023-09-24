package com.example.nyatta.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.nyatta.GetListingsQuery

interface ListingsRepository {
    suspend fun getListings(): ApolloResponse<GetListingsQuery.Data>
}

class GqlListingsRepository(
    private val client: ApolloClient
): ListingsRepository {
    override suspend fun getListings(): ApolloResponse<GetListingsQuery.Data> {
        return client.query(GetListingsQuery()).execute()
    }
}