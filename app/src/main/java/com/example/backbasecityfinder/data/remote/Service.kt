package com.example.backbasecityfinder.data.remote

import com.example.backbasecityfinder.data.remote.dto.City
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface Service {

    suspend fun getCities(): List<City>

    companion object {
        fun create(): Service {
            return ServiceImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}