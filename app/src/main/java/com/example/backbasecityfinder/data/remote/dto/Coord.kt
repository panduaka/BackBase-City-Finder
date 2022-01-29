package com.example.backbasecityfinder.data.remote.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Coord(
    var lon: Double,
    var lat: Double,
)
