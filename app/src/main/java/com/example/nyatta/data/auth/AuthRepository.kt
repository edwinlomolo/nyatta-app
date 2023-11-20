package com.example.nyatta.data.auth

import com.example.nyatta.data.daos.TokenDao
import com.example.nyatta.data.model.Token
import com.example.nyatta.data.model.User
import com.example.nyatta.network.NyattaGqlApiRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signLocalUser(token: Token)

    fun getAuthToken(): Flow<List<Token>>

    suspend fun signIn(phone: String)

    suspend fun recycleUser(isLandlord: Boolean, gps: LatLng)

    suspend fun storeUserLocation(token: Token, gps: LatLng)

    suspend fun updateUser(user: User)
}

class OfflineAuthRepository(
    private val tokenDao: TokenDao,
    private val nyattaGqlApiRepository: NyattaGqlApiRepository
): AuthRepository {

    override suspend fun signLocalUser(token: Token) = tokenDao.insertToken(token)

    override fun getAuthToken(): Flow<List<Token>> = tokenDao.getAuthToken()

    override suspend fun signIn(phone: String) {
        val res = nyattaGqlApiRepository.signIn(phone).dataOrThrow()
        tokenDao.insertToken(Token(token = res.signIn.Token))
    }

    override suspend fun recycleUser(isLandlord: Boolean, gps: LatLng) {
        val res = nyattaGqlApiRepository.refreshUser().dataOrThrow()
        tokenDao.updateAuthToken(
            Token(
                isLandlord = isLandlord,
                token = res.refreshToken.Token,
                lat = gps.latitude,
                lng = gps.longitude
            )
        )
    }

    override suspend fun storeUserLocation(token: Token, gps: LatLng) {
        tokenDao.updateAuthToken(
            Token(
                token = token.token,
                lat = gps.latitude,
                lng = gps.longitude
            )
        )
    }

    override suspend fun updateUser(user: User) {
        nyattaGqlApiRepository.updateUser(user = user)
    }
}