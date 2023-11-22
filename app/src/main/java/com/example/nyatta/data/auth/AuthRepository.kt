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

    suspend fun recycleUser(tokenId: Int, gps: LatLng)

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
        tokenDao.insertToken(
            Token(
                token = res.signIn.Token,
                isLandlord = res.signIn.user.is_landlord,
                subscribeRetries = res.signIn.user.subscribe_retries
            )
        )
    }

    override suspend fun recycleUser(tokenId: Int, gps: LatLng) {
        val res = nyattaGqlApiRepository.refreshUser().dataOrThrow()
        tokenDao.updateAuthToken(
            Token(
                id = tokenId,
                isLandlord = res.refreshToken.user.is_landlord,
                token = res.refreshToken.Token,
                subscribeRetries = res.refreshToken.user.subscribe_retries,
                lat = gps.latitude,
                lng = gps.longitude
            )
        )
    }

    override suspend fun storeUserLocation(token: Token, gps: LatLng) {
        tokenDao.updateAuthToken(
            Token(
                id = token.id,
                token = token.token,
                subscribeRetries = token.subscribeRetries,
                lat = gps.latitude,
                lng = gps.longitude
            )
        )
    }

    override suspend fun updateUser(user: User) {
        nyattaGqlApiRepository.updateUser(user = user)
    }
}