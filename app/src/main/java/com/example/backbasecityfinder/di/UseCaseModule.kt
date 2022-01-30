package com.example.backbasecityfinder.di

import com.example.backbasecityfinder.domain.interactors.GetCitiesUserCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetCitiesUserCase(get()) }
}