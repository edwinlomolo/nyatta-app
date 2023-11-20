package com.example.nyatta.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.nyatta.data.model.Token
import kotlinx.coroutines.flow.Flow

@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToken(token: Token)

    @Query("SELECT * FROM tokens LIMIT 1")
    fun getAuthToken(): Flow<List<Token>>

    @Update
    suspend fun updateAuthToken(token: Token)
}