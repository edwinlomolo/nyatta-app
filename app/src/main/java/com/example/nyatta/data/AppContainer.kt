package com.example.nyatta.data

import com.apollographql.apollo3.ApolloClient

interface AppContainer {
    val helloRepository: HelloRepository
}
class DefaultContainer: AppContainer {
    private val baseUrl =
        "http://127.0.0.1:4000/api"

    private val apolloClient: ApolloClient = ApolloClient.Builder()
            .serverUrl(baseUrl)
            .build()

    override val helloRepository: HelloRepository by lazy {
        ApolloHelloRepository(apolloClient)
    }
}