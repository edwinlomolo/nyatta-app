package com.example.nyatta.data

import kotlin.random.Random

data class Amenity(
    val category: String,
    val label: String,
    val id: Int = Random.nextInt()
)

val amenities = listOf<Amenity>(
    Amenity("Kitchen", "Open Kitchen Plan"),
    Amenity("Kitchen", "Kitchen"),
    Amenity("Bedroom", "Closet/Dresser"),
    Amenity("Bathroom", "Hot Shower"),
    Amenity("Internet", "Safaricom Home Fibre"),
    Amenity("Kitchen", "Hot Water Sink"),
    Amenity("Dining", "Dining Area/Space"),
    Amenity("Bedroom", "Door Locks"),
    Amenity("Outdoor", "Balcony"),
    Amenity("Outdoor", "Front Porch"),
    Amenity("Parking", "Free Premises Parking"),
    Amenity("Internet", "Zuku Home Fibre"),
    Amenity("Security", "CCTV Camera"),
    Amenity("Television", "TV Cable"),
    Amenity("Safety", "Smoke Alarm"),
    Amenity("Water", "Piped Water")
)