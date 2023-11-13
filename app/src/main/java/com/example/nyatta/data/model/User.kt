package com.example.nyatta.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Represent a single row in the db
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val phone: String = "",
    val isLandlord: Boolean = false,
    val token: String = ""
)
