package com.example.nyatta.data.auth

import com.example.nyatta.data.daos.UserDao
import com.example.nyatta.data.model.User
import com.example.nyatta.network.NyattaGqlApiRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUser(user: User)

    fun getUser(): Flow<List<User>>

    suspend fun signIn(phone: String)

    suspend fun recycleUser(user: User)

    suspend fun storeUserLocation(user: User, gps: LatLng)

    suspend fun updateUser(user: User)
}

class OfflineAuthRepository(
    private val userDao: UserDao,
    private val nyattaGqlApiRepository: NyattaGqlApiRepository
): AuthRepository {

    override suspend fun signUser(user: User) = userDao.insert(user)

    override fun getUser(): Flow<List<User>> = userDao.getUser()

    override suspend fun signIn(phone: String) {
        val res = nyattaGqlApiRepository.signIn(phone).dataOrThrow()
        userDao.insert(
            user = User(
                firstName = res.signIn.user.first_name,
                lastName = res.signIn.user.last_name,
                avatar = res.signIn.user.avatar?.upload ?: "",
                phone =res.signIn.user.phone,
                token = res.signIn.Token,
                isLandlord = res.signIn.user.is_landlord
            )
        )
    }

    override suspend fun recycleUser(user: User) {
        val res = nyattaGqlApiRepository.signIn(user.phone).dataOrThrow()
        userDao.updateUser(
            user = User(
                firstName = res.signIn.user.first_name,
                lastName = res.signIn.user.last_name,
                avatar = res.signIn.user.avatar?.upload ?: "",
                phone =res.signIn.user.phone,
                token = res.signIn.Token,
                isLandlord = res.signIn.user.is_landlord,
                lat = user.lat,
                lng = user.lng
            )
        )
    }

    override suspend fun storeUserLocation(user: User, gps: LatLng) {
        userDao.updateUser(
            user = User(
                firstName = user.firstName,
                lastName = user.lastName,
                phone = user.phone,
                avatar = user.avatar,
                lat = gps.latitude,
                lng = gps.longitude,
                isLandlord = user.isLandlord,
                token = user.token
            )
        )
    }

    override suspend fun updateUser(user: User) {
        nyattaGqlApiRepository.updateUser(user = user)
    }
}