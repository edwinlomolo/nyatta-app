package com.example.nyatta.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["phone"], unique = true),
        Index(value = ["is_landlord"])
    ]
)
data class User(
    @PrimaryKey val phone: String = "",
    @ColumnInfo(name = "is_landlord") val isLandlord: Boolean = false,
    val token: String = "",
    @ColumnInfo(name = "first_name") val firstName: String = "",
    @ColumnInfo(name = "last_name") val lastName: String = "",
    val avatar: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0
)
