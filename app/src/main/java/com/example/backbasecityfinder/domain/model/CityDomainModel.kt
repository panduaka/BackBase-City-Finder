package com.example.backbasecityfinder.domain.model

data class CityDomainModel(
    val title: String,
    val coordinates: CoordDomainModel
)

data class CoordDomainModel(
    val latitude: Double,
    val longitude: Double
)