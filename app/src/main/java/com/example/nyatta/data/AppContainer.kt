package com.example.nyatta.data

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.example.nyatta.data.apollographql.ApolloGraphqlRepository
import com.example.nyatta.data.apollographql.ApolloRepository
import com.example.nyatta.data.auth.OfflineAuthRepository
import com.example.nyatta.data.hello.GqlHelloRepository
import com.example.nyatta.data.hello.HelloRepository
import com.example.nyatta.data.listings.GqlListingsRepository
import com.example.nyatta.data.listings.ListingsRepository
import com.example.nyatta.data.towns.GqlTownsRepository
import com.example.nyatta.data.towns.TownsRepository

interface AppContainer {
    val helloRepository: HelloRepository
    val listingsRepository: ListingsRepository
    val townsRepository: TownsRepository
    val authRepository: OfflineAuthRepository
    val apolloClient: ApolloClient
    val apolloRepository: ApolloRepository
}

val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory("nyatta.db")

class DefaultContainer(private val context: Context): AppContainer {
    private val baseUrl =
        "https://80f2-102-217-127-1.ngrok-free.app/api"

    override val apolloClient = ApolloClient.Builder()
        .serverUrl(baseUrl)
        .fetchPolicy(FetchPolicy.NetworkFirst)
        .normalizedCache(sqlNormalizedCacheFactory)
        .build()

    override val helloRepository: HelloRepository by lazy {
        GqlHelloRepository(apolloClient)
    }

    override val listingsRepository: ListingsRepository by lazy {
        GqlListingsRepository(apolloClient)
    }

    override val apolloRepository: ApolloRepository by lazy {
        ApolloGraphqlRepository(apolloClient)
    }

    override val townsRepository: TownsRepository by lazy {
        GqlTownsRepository(apolloClient)
    }

    override val authRepository: OfflineAuthRepository by lazy {
        OfflineAuthRepository(
            userDao = NyattaDatabase.getDatabase(context).userDao(),
            client = apolloClient
        )
    }
}