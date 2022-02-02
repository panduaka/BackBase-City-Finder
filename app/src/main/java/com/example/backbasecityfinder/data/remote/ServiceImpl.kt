package com.example.backbasecityfinder.data.remote

import com.example.backbasecityfinder.data.remote.dto.City
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*

class ServiceImpl(
    private val client: HttpClient
) : Service {

    var cities = listOf<City>()

    override suspend fun getCities(): List<City> {
        return try {
            cities = client.get { url(HttpRoutes.POSTS) }
            return cities
        } catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch(e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getFetchedCities(): List<City> {
        return cities
    }
}