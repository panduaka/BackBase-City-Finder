package com.example.backbasecityfinder.data.mapper

import com.example.backbasecityfinder.data.remote.dto.City
import com.example.backbasecityfinder.domain.model.CityDomainModel

fun City.mapToUIModel() = CityDomainModel(
    title = "${this.name}, ${this.country}", // Concatenation of the name of the city and country
    latitude = this.coord.lat,
    longitude = this.coord.lon
)