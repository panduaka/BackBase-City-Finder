package com.example.backbasecityfinder.data.remote.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class City(
    var country: String,
    var name: String,
    var _id: Int,
    var coord: Coord,
)
