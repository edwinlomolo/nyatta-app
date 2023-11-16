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
import com.example.nyatta.data.daos.UserDao
import com.example.nyatta.data.hello.GqlHelloRepository
import com.example.nyatta.data.hello.HelloRepository
import com.example.nyatta.data.listings.GqlListingsRepository
import com.example.nyatta.data.listings.ListingsRepository
import com.example.nyatta.data.rest.RestApiRepository
import com.example.nyatta.data.towns.GqlTownsRepository
import com.example.nyatta.data.towns.TownsRepository
import com.example.nyatta.network.NyattaRestApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val helloRepository: HelloRepository
    val listingsRepository: ListingsRepository
    val townsRepository: TownsRepository
    val authRepository: OfflineAuthRepository
    val apolloClient: ApolloClient
    val restApiRepository: RestApiRepository
}
private const val baseNyattaGqlApiUrl =
    "https://80f2-102-217-127-1.ngrok-free.app/api"
private const val baseNyattaRestApiUrl =
    "https://80f2-102-217-127-1.ngrok-free.app"

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

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseNyattaRestApiUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val nyattaRestApiService: NyattaRestApiService by lazy {
        retrofit.create(NyattaRestApiService::class.java)
    }

    override val restApiRepository: RestApiRepository by lazy {
        RestApiRepository(nyattaRestApiService)
    }

    override val apolloClient by lazy {
        ApolloClient.Builder()
            .serverUrl(baseNyattaGqlApiUrl)
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