package com.example.backbasecityfinder.domain.interactors

import com.example.backbasecityfinder.domain.repository.CityRepository

class GetCitiesUserCase(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke() {

    }
}