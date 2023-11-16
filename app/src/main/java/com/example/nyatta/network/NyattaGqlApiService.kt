package com.example.nyatta.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Upload
import com.example.nyatta.UploadImageMutation

interface NyattaGqlApiService {
    suspend fun uploadImage(file: Upload): ApolloResponse<UploadImageMutation.Data>
}

class NyattaGqlApiRepository(
    private val apolloClient: ApolloClient
): NyattaGqlApiService {
    override suspend fun uploadImage(file: Upload): ApolloResponse<UploadImageMutation.Data> {
        return apolloClient.mutation(UploadImageMutation(file = file)).execute()
    }
}