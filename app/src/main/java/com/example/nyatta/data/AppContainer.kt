package com.example.nyatta.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

interface AppContainer {
    val helloRepository: HelloRepository
    val listingsRepository: ListingsRepository
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
}