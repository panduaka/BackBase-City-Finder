package com.example.backbasecityfinder.data.remote

import com.example.backbasecityfinder.data.remote.dto.City
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*

class ServiceImpl(
    private val client: HttpClient
) : Service {

    override suspend fun getCities(): List<City> {
        return try {
            client.get { url(HttpRoutes.POSTS) }
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
}