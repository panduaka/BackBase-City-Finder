package com.example.backbasecityfinder.di

import com.example.backbasecityfinder.data.remote.Service
import com.example.backbasecityfinder.data.remote.repository.CityRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { CityRepositoryImpl(get()) }
}

val serviceModule = module {
    single { Service.create() }
}