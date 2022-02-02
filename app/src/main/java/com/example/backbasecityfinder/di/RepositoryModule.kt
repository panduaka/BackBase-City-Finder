package com.example.backbasecityfinder.di

import com.example.backbasecityfinder.data.remote.Service
import com.example.backbasecityfinder.data.remote.repository.CityRepositoryImpl
import com.example.backbasecityfinder.domain.repository.CityRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { CityRepositoryImpl(get()) as CityRepository}
}

val serviceModule = module {
    single { Service.create() }
}