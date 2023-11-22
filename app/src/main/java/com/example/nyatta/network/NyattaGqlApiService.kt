package com.example.nyatta.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.optimisticUpdates
import com.example.nyatta.CreatePaymentMutation
import com.example.nyatta.CreatePropertyMutation
import com.example.nyatta.GetUserPropertiesQuery
import com.example.nyatta.GetUserQuery
import com.example.nyatta.RefreshTokenQuery
import com.example.nyatta.SignInMutation
import com.example.nyatta.UpdateUserInfoMutation
import com.example.nyatta.data.model.User
import com.example.nyatta.type.CaretakerInput
import com.example.nyatta.type.GpsInput
import com.example.nyatta.viewmodels.ImageState
import com.example.nyatta.viewmodels.PropertyData
import com.google.android.gms.maps.model.LatLng

interface NyattaGqlApiService {
    suspend fun signIn(phone: String): ApolloResponse<SignInMutation.Data>

    suspend fun createPayment(phone: String, amount: String): ApolloResponse<CreatePaymentMutation.Data>

    suspend fun updateUser(user: User): ApolloResponse<UpdateUserInfoMutation.Data>

    suspend fun refreshUser(): ApolloResponse<RefreshTokenQuery.Data>

    suspend fun getUser(): ApolloResponse<GetUserQuery.Data>

    suspend fun getUserProperties(): ApolloResponse<GetUserPropertiesQuery.Data>

    suspend fun createProperty(type: String, deviceLocation: LatLng, property: PropertyData): ApolloResponse<CreatePropertyMutation.Data>
}

class NyattaGqlApiRepository(
    private val apolloClient: ApolloClient
): NyattaGqlApiService {
    override suspend fun signIn(phone: String): ApolloResponse<SignInMutation.Data> {
        return apolloClient
            .mutation(SignInMutation(phone = phone))
            .execute()
    }

    override suspend fun createPayment(phone: String, amount: String): ApolloResponse<CreatePaymentMutation.Data> {
        return apolloClient
            .mutation(CreatePaymentMutation(phone = phone, amount = amount))
            .execute()
    }

    override suspend fun updateUser(user: User): ApolloResponse<UpdateUserInfoMutation.Data> {
        return apolloClient
            .mutation(
                UpdateUserInfoMutation(
                    firstName = user.firstName,
                    lastName = user.lastName,
                    avatar = user.avatar
                )
            )
            .execute()
    }

    override suspend fun refreshUser(): ApolloResponse<RefreshTokenQuery.Data> {
        return apolloClient.query(RefreshTokenQuery()).fetchPolicy(FetchPolicy.NetworkFirst).execute()
    }

    override suspend fun getUser(): ApolloResponse<GetUserQuery.Data> {
        return apolloClient.query(GetUserQuery()).fetchPolicy(FetchPolicy.NetworkFirst).execute()
    }

    override suspend fun getUserProperties(): ApolloResponse<GetUserPropertiesQuery.Data> {
        return apolloClient.query(GetUserPropertiesQuery()).fetchPolicy(FetchPolicy.NetworkFirst).execute()
    }

    override suspend fun createProperty(type: String, deviceLocation: LatLng, property: PropertyData): ApolloResponse<CreatePropertyMutation.Data> {
        return apolloClient.mutation(
            CreatePropertyMutation(
                name = property.description,
                thumbnail = if(property.thumbnail is ImageState.Success && property.thumbnail.imageUri != null) property.thumbnail.imageUri else User().avatar,
                type = type,
                isCaretaker = property.isCaretaker,
                location = GpsInput(
                    lat = deviceLocation.latitude,
                    lng = deviceLocation.longitude
                ),
                caretaker = CaretakerInput(
                    first_name = property.caretaker.firstName,
                    last_name = property.caretaker.lastName,
                    phone = property.caretaker.phone,
                    image = if (property.caretaker.image is ImageState.Success && property.caretaker.image.imageUri != null) property.caretaker.image.imageUri else User().avatar
                )
            )
        ).execute()
    }
}