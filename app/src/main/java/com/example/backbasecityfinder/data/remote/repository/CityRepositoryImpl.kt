package com.example.backbasecityfinder.data.remote.repository

import com.example.backbasecityfinder.data.remote.dto.City
import com.example.backbasecityfinder.domain.repository.CityRepository
import com.example.backbasecityfinder.data.remote.Service

class CityRepositoryImpl(
    private val service: Service
) : CityRepository {
    override suspend fun getCities(): List<City> {
        return service.getCities()
    }
}