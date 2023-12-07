package com.example.nyatta.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.example.nyatta.AddUnitMutation
import com.example.nyatta.CreatePaymentMutation
import com.example.nyatta.CreatePropertyMutation
import com.example.nyatta.GetNearByUnitsQuery
import com.example.nyatta.GetPropertyQuery
import com.example.nyatta.GetUnitQuery
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
import com.google.i18n.phonenumbers.PhoneNumberUtil

interface NyattaGqlApiService {
    suspend fun signIn(phone: String): ApolloResponse<SignInMutation.Data>

    suspend fun createPayment(token: Token, phone: String, amount: String): ApolloResponse<CreatePaymentMutation.Data>

    suspend fun updateUser(user: User): ApolloResponse<UpdateUserInfoMutation.Data>

    suspend fun refreshUser(): ApolloResponse<RefreshTokenQuery.Data>

    suspend fun getUser(): ApolloResponse<GetUserQuery.Data>

    suspend fun getUserProperties(): ApolloResponse<GetUserPropertiesQuery.Data>

    suspend fun createProperty(type: String, deviceLocation: LatLng, property: PropertyData): ApolloResponse<CreatePropertyMutation.Data>

    suspend fun addUnit(type: String, deviceLocation: LatLng, propertyData: PropertyData, apartmentData: ApartmentData): ApolloResponse<AddUnitMutation.Data>

    suspend fun getNearByListings(deviceLocation: LatLng): ApolloResponse<GetNearByUnitsQuery.Data>

    suspend fun getUnit(id: String): ApolloResponse<GetUnitQuery.Data>

    suspend fun getProperty(id: String): ApolloResponse<GetPropertyQuery.Data>
}

class NyattaGqlApiRepository(
    private val apolloClient: ApolloClient,
    private val tokenDao: TokenDao
): NyattaGqlApiService {
    private val phoneUtil = PhoneNumberUtil.getInstance()

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
        val tel = phoneUtil.parse(property.caretaker.phone, "KE")
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
                    phone = tel.countryCode.toString()+tel.nationalNumber.toString(),
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
        val isCaretaker: Optional<Boolean> = Optional.presentIfNotNull(propertyData.isCaretaker)
        val propertyId: Optional<Any?> = Optional.presentIfNotNull(apartmentData.associatedToProperty?.id)
        // TODO to make phoneutil happy give it a default regional number
        val phone = phoneUtil.parse(propertyData.caretaker.phone.ifEmpty { "0700000000" }, "KE")
        val caretaker: Optional<CaretakerInput> = Optional.presentIfNotNull(CaretakerInput(
            first_name = propertyData.caretaker.firstName,
            last_name = propertyData.caretaker.lastName,
            image = (propertyData.caretaker.image as ImageState.Success).imageUri ?: User().avatar,
            phone = phone.countryCode.toString()+phone.nationalNumber.toString()
        ))

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
                location = deviceGps,
                uploads = images,
                isCaretaker = isCaretaker,
                caretaker = caretaker,
                state = if (apartmentData.state.toString() == "VACANT") UnitState.VACANT else UnitState.OCCUPIED
            )
        ).execute()
    }

    override suspend fun getNearByListings(deviceLocation: LatLng): ApolloResponse<GetNearByUnitsQuery.Data> {
        return apolloClient.query(
            GetNearByUnitsQuery(
                lat = deviceLocation.latitude,
                lng = deviceLocation.longitude
            )
        ).execute()
    }

    override suspend fun getUnit(id: String): ApolloResponse<GetUnitQuery.Data> {
        return apolloClient.query(GetUnitQuery(id)).execute()
    }

    override suspend fun getProperty(id: String): ApolloResponse<GetPropertyQuery.Data> {
        return apolloClient.query(GetPropertyQuery(id)).fetchPolicy(FetchPolicy.NetworkFirst).execute()
    }
}