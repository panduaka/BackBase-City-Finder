package com.example.backbasecityfinder

import android.app.Application
import com.example.backbasecityfinder.di.repositoryModule
import com.example.backbasecityfinder.di.serviceModule
import com.example.backbasecityfinder.di.useCaseModule
import com.example.backbasecityfinder.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CityFinderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@CityFinderApplication)
            modules(viewModelModule, repositoryModule, serviceModule, useCaseModule)
        }
    }
}