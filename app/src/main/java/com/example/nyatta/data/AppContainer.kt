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
import com.example.nyatta.data.daos.TokenDao
import com.example.nyatta.data.rest.RestApiRepository
import com.example.nyatta.data.towns.GqlTownsRepository
import com.example.nyatta.data.towns.TownsRepository
import com.example.nyatta.network.NyattaGqlApiRepository
import com.example.nyatta.network.NyattaRestApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import io.sentry.apollo3.sentryTracing

interface AppContainer {
    val townsRepository: TownsRepository
    val authRepository: OfflineAuthRepository
    val apolloClient: ApolloClient
    val restApiRepository: RestApiRepository
    val nyattaGqlApiRepository: NyattaGqlApiRepository
}
private const val baseNyattaGqlApiUrl =
    "https://68d3-102-217-127-1.ngrok-free.app/api"
private const val baseNyattaRestApiUrl =
    "https://68d3-102-217-127-1.ngrok-free.app"

val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory("nyatta.db")

class AuthorizationInterceptor(
    private val authRepository: TokenDao
): HttpInterceptor {
    private val mutex = Mutex()

    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        val token = mutex.withLock {
            authRepository.getAuthToken()
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

    private val okhttpClient = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .callTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseNyattaRestApiUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okhttpClient)
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
                    NyattaDatabase.getDatabase(context).tokenDao()
                )
            )
            .sentryTracing(captureFailedRequests = true)
            .normalizedCache(sqlNormalizedCacheFactory)
            .build()
    }

    override val nyattaGqlApiRepository: NyattaGqlApiRepository by lazy {
        NyattaGqlApiRepository(
            apolloClient,
            NyattaDatabase.getDatabase(context).tokenDao()
        )
    }

    override val townsRepository: TownsRepository by lazy {
        GqlTownsRepository(apolloClient)
    }

    override val authRepository: OfflineAuthRepository by lazy {
        OfflineAuthRepository(
            tokenDao = NyattaDatabase.getDatabase(context).tokenDao(),
            nyattaGqlApiRepository = nyattaGqlApiRepository
        )
    }
}