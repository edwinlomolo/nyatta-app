package com.example.nyatta.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
data class Token(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val token: String = "",
    val subscribeTried: Boolean = false,
    val isLandlord: Boolean = false,
    val subscribeRetries: Int = 0,
    val lat: Double = 0.0,
    val lng: Double = 0.0
)
