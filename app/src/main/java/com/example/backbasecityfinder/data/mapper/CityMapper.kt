package com.example.backbasecityfinder.data.mapper

import com.example.backbasecityfinder.data.remote.dto.City
import com.example.backbasecityfinder.domain.model.CityDomainModel
import com.example.backbasecityfinder.domain.model.CoordDomainModel

fun City.mapToUIModel() = CityDomainModel(
    title = "${this.name}, ${this.country}",
    coordinates = CoordDomainModel(
        latitude = this.coord.lat,
        longitude = this.coord.lon
    )
)