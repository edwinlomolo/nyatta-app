package com.example.nyatta.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.example.nyatta.AddUnitMutation
import com.example.nyatta.CreatePaymentMutation
import com.example.nyatta.CreatePropertyMutation
import com.example.nyatta.GetUserPropertiesQuery
import com.example.nyatta.GetUserQuery
import com.example.nyatta.RefreshTokenQuery
import com.example.nyatta.SignInMutation
import com.example.nyatta.UpdateUserInfoMutation
import com.example.nyatta.data.daos.TokenDao
import com.example.nyatta.data.model.Token
import com.example.nyatta.data.model.User
import com.example.nyatta.type.CaretakerInput
import com.example.nyatta.type.GpsInput
import com.example.nyatta.type.UnitAmenityInput
import com.example.nyatta.type.UnitBedroomInput
import com.example.nyatta.type.UnitState
import com.example.nyatta.type.UploadImages
import com.example.nyatta.viewmodels.ApartmentData
import com.example.nyatta.viewmodels.ImageState
import com.example.nyatta.viewmodels.PropertyData
import com.google.android.gms.maps.model.LatLng

interface NyattaGqlApiService {
    suspend fun signIn(phone: String): ApolloResponse<SignInMutation.Data>

    suspend fun createPayment(token: Token, phone: String, amount: String): ApolloResponse<CreatePaymentMutation.Data>

    suspend fun updateUser(user: User): ApolloResponse<UpdateUserInfoMutation.Data>

    suspend fun refreshUser(): ApolloResponse<RefreshTokenQuery.Data>

    suspend fun getUser(): ApolloResponse<GetUserQuery.Data>

    suspend fun getUserProperties(): ApolloResponse<GetUserPropertiesQuery.Data>

    suspend fun createProperty(type: String, deviceLocation: LatLng, property: PropertyData): ApolloResponse<CreatePropertyMutation.Data>

    suspend fun addUnit(type: String, deviceLocation: LatLng, property: PropertyData, apartmentData: ApartmentData): ApolloResponse<AddUnitMutation.Data>
}

class NyattaGqlApiRepository(
    private val apolloClient: ApolloClient,
    private val tokenDao: TokenDao
): NyattaGqlApiService {
    override suspend fun signIn(phone: String): ApolloResponse<SignInMutation.Data> {
        return apolloClient
            .mutation(SignInMutation(phone = phone))
            .execute()
    }

    override suspend fun createPayment(token: Token, phone: String, amount: String): ApolloResponse<CreatePaymentMutation.Data> {
        tokenDao.updateAuthToken(
            token = Token(
                id = token.id,
                subscribeRetries = token.subscribeRetries,
                subscribeTried = true,
                token = token.token,
                isLandlord = token.isLandlord
            )
        )
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

    override suspend fun addUnit(type: String, deviceLocation: LatLng, propertyData: PropertyData, apartmentData: ApartmentData): ApolloResponse<AddUnitMutation.Data> {
        val images = apartmentData.images.entries.flatMap { entry ->
            entry.value.map { value ->
                UploadImages((value as ImageState.Success).imageUri ?: User().avatar, entry.key)
            }
        }
        val deviceGps: Optional<GpsInput?> = Optional.presentIfNotNull(
            GpsInput(
                lat = deviceLocation.latitude,
                lng = deviceLocation.longitude
            )
        )
        val propertyId: Optional<Any?> = Optional.presentIfNotNull(apartmentData.associatedToProperty?.id)
        val propertyLocation: Optional<GpsInput?> = Optional.presentIfNotNull(
            GpsInput(
                lat = apartmentData.associatedToProperty?.location?.lat ?: 0.0,
                lng = apartmentData.associatedToProperty?.location?.lng ?: 0.0
            )
        )
        val caretaker: Optional<CaretakerInput> = Optional.presentIfNotNull(CaretakerInput(
            first_name = propertyData.caretaker.firstName,
            last_name = propertyData.caretaker.lastName,
            image = (propertyData.caretaker.image as ImageState.Success).imageUri ?: User().avatar,
            phone = propertyData.caretaker.phone
        ))
        val isCaretaker: Optional<Boolean> = Optional.presentIfNotNull(propertyData.isCaretaker)

        return apolloClient.mutation(
            AddUnitMutation(
                propertyId = propertyId,
                name = apartmentData.description,
                baths = apartmentData.bathrooms.toInt(),
                type = type,
                price = apartmentData.price,
                bedrooms = apartmentData.bedrooms
                    .map {
                        UnitBedroomInput(
                           bedroomNumber = it.number,
                            enSuite = it.enSuite,
                            master = it.master
                        )
                    },
                amenities = apartmentData.selectedAmenities
                    .map {
                        UnitAmenityInput(
                            name = it.label,
                            category = it.category
                        )
                    },
                location = if (type == "Unit") propertyLocation else deviceGps,
                uploads = images,
                isCaretaker = isCaretaker,
                caretaker = caretaker,
                state = if (apartmentData.state.toString() == "VACANT") UnitState.VACANT else UnitState.OCCUPIED
            )
        ).execute()
    }
}