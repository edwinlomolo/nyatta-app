package com.example.nyatta.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
data class Token(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val token: String = "",
    val isLandlord: Boolean = false,
    val lat: Double = 0.0,
    val lng: Double = 0.0
)
