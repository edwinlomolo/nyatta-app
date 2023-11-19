package com.example.nyatta.data.auth

import android.util.Log
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.data.daos.UserDao
import com.example.nyatta.data.model.User
import com.example.nyatta.network.NyattaGqlApiRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUser(user: User)

    fun getUser(): Flow<List<User>>

    suspend fun signIn(phone: String)

    suspend fun recycleUser(phone: String)

    suspend fun storeUserLocation(phone: String, gps: LatLng)
}

class OfflineAuthRepository(
    private val userDao: UserDao,
    private val nyattaGqlApiRepository: NyattaGqlApiRepository
): AuthRepository {

    override suspend fun signUser(user: User) = userDao.insert(user)

    override fun getUser(): Flow<List<User>> = userDao.getUser()

    override suspend fun signIn(phone: String) {
        try {
            val res = nyattaGqlApiRepository.signIn(phone).dataOrThrow()
            userDao.insert(
                user = User(
                    phone =res.signIn.user.phone,
                    token = res.signIn.Token,
                    isLandlord = res.signIn.user.is_landlord
                )
            )
        } catch(e: ApolloException) {
            e.localizedMessage?.let { Log.e("SignInUserOperationError", it) }
        }
    }

    override suspend fun recycleUser(phone: String) {
        try {
            val res = nyattaGqlApiRepository.signIn(phone).dataOrThrow()
            userDao.updateUser(
                user = User(
                    phone =res.signIn.user.phone,
                    token = res.signIn.Token,
                    isLandlord = res.signIn.user.is_landlord
                )
            )
        } catch(e: ApolloException) {
            e.localizedMessage?.let { Log.e("SignInUserOperationError", it) }
        }
    }

    override suspend fun storeUserLocation(phone: String, gps: LatLng) {
        try {
            userDao.updateUser(
                user = User(
                    phone = phone,
                    lat = gps.latitude,
                    lng = gps.longitude
                )
            )
        } catch(e: Throwable) {
            e.localizedMessage?.let { Log.e("UpdateUserLocationOperationError", it) }
        }
    }
}