package com.example.nyatta.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nyatta.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flow<List<User>>
    @Delete
    suspend fun deleteUser(user: User)
}