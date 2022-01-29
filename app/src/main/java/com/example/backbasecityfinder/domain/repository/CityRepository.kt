package com.example.backbasecityfinder.domain.repository

import com.example.backbasecityfinder.data.remote.dto.City

interface CityRepository {
    suspend fun getCities():List<City>
}