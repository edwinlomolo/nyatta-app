package com.example.nyatta.network

import com.apollographql.apollo3.ApolloClient

interface NyattaGqlApiService {
}

class NyattaGqlApiRepository(
    private val apolloClient: ApolloClient
): NyattaGqlApiService {
}