package com.example.nyatta.data

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.example.nyatta.data.auth.OfflineAuthRepository
import com.example.nyatta.data.hello.GqlHelloRepository
import com.example.nyatta.data.hello.HelloRepository
import com.example.nyatta.data.listings.GqlListingsRepository
import com.example.nyatta.data.listings.ListingsRepository
import com.example.nyatta.data.towns.GqlTownsRepository
import com.example.nyatta.data.towns.TownsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

interface AppContainer {
    val helloRepository: HelloRepository
    val listingsRepository: ListingsRepository
    val townsRepository: TownsRepository
    val authRepository: OfflineAuthRepository
}

class AuthorizationInterceptor (val token: String): HttpInterceptor {
    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        return chain.proceed(
            request
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        )
    }
}

val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory("nyatta.db")

class DefaultContainer(private val context: Context): AppContainer {
    private val userDao = NyattaDatabase.getDatabase(context).userDao()
    private val applicationScope = CoroutineScope(SupervisorJob())
    private lateinit var token: String
    private lateinit var client: ApolloClient

    init {
        applicationScope.launch {
            userDao.getUser().collect {
                token = if (it.isNotEmpty()) it[0].token else ""
                client = ApolloClient.Builder()
                    .serverUrl(baseUrl)
                    .addHttpInterceptor(AuthorizationInterceptor(token))
                    .normalizedCache(sqlNormalizedCacheFactory)
                    .build()
            }
        }
    }

    private val baseUrl =
        "https://8a76-102-217-127-1.ngrok.io/api"

    override val helloRepository: HelloRepository by lazy {
        GqlHelloRepository(client)
    }

    override val listingsRepository: ListingsRepository by lazy {
        GqlListingsRepository(client)
    }

    override val townsRepository: TownsRepository by lazy {
        GqlTownsRepository(client)
    }

    override val authRepository: OfflineAuthRepository by lazy {
        OfflineAuthRepository(
            userDao = userDao,
            client = client
        )
    }
}