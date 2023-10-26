package com.example.nyatta.data

import com.apollographql.apollo3.ApolloClient
import com.example.nyatta.data.auth.AuthRepository
import com.example.nyatta.data.auth.GqlAuthRepository
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
    val authRepository: AuthRepository
}

// TODO: Sample intercept: can refactor it to handle authentication?
/**
 * class LoggingApolloInterceptor: ApolloInterceptor {
 *     override fun <D : Operation.Data> intercept(request: ApolloRequest<D>, chain: ApolloInterceptorChain): Flow<ApolloResponse<D>> {
 *         return chain.proceed(request).onEach {
 *             println("Received response for ${request.operation.name()}")
 *         }
 *     }
 * }
 */

class DefaultContainer: AppContainer {
    private val baseUrl =
        "https://stagingapi.nyatta.app/api"

    private val client = ApolloClient.Builder()
        .serverUrl(baseUrl)
        //.addInterceptor(LoggingApolloInterceptor())
        .build()    

    override val helloRepository: HelloRepository by lazy {
        GqlHelloRepository(client)
    }

    override val listingsRepository: ListingsRepository by lazy {
        GqlListingsRepository(client)
    }

    override val townsRepository: TownsRepository by lazy {
        GqlTownsRepository(client)
    }

    override val authRepository: AuthRepository by lazy {
        GqlAuthRepository(client)
    }
}