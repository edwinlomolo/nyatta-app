package com.example.nyatta.data

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.example.nyatta.data.auth.OfflineAuthRepository
import com.example.nyatta.data.daos.UserDao
import com.example.nyatta.data.hello.GqlHelloRepository
import com.example.nyatta.data.hello.HelloRepository
import com.example.nyatta.data.listings.GqlListingsRepository
import com.example.nyatta.data.listings.ListingsRepository
import com.example.nyatta.data.towns.GqlTownsRepository
import com.example.nyatta.data.towns.TownsRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface AppContainer {
    val helloRepository: HelloRepository
    val listingsRepository: ListingsRepository
    val townsRepository: TownsRepository
    val authRepository: OfflineAuthRepository
    val apolloClient: ApolloClient
}

val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory("nyatta.db")

class AuthorizationInterceptor(
    private val authRepository: UserDao
): HttpInterceptor {
    private val mutex = Mutex()

    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        val token = mutex.withLock {
            authRepository.getUser()
                .firstOrNull()
        }

        return if (token!!.isNotEmpty()) {
            chain.proceed(
                request.newBuilder().addHeader("Authorization", "Bearer ${token.first().token}").build()
            )
        } else {
            chain.proceed(
                request.newBuilder().build()
            )
        }

    }
}

class DefaultContainer(private val context: Context): AppContainer {
    private val baseUrl =
        "https://80f2-102-217-127-1.ngrok-free.app/api"

    override val apolloClient by lazy {
        ApolloClient.Builder()
            .serverUrl(baseUrl)
            .fetchPolicy(FetchPolicy.NetworkFirst)
            .addHttpInterceptor(
                AuthorizationInterceptor(
                    NyattaDatabase.getDatabase(context).userDao()
                )
            )
            .normalizedCache(sqlNormalizedCacheFactory)
            .build()
    }

    override val helloRepository: HelloRepository by lazy {
        GqlHelloRepository(apolloClient)
    }

    override val listingsRepository: ListingsRepository by lazy {
        GqlListingsRepository(apolloClient)
    }

    override val townsRepository: TownsRepository by lazy {
        GqlTownsRepository(apolloClient)
    }

    override val authRepository: OfflineAuthRepository by lazy {
        OfflineAuthRepository(
            NyattaDatabase.getDatabase(context).userDao(),
            client = apolloClient
        )
    }
}