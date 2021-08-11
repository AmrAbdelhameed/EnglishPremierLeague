package com.example.englishpremierleague

import androidx.multidex.MultiDexApplication
import com.example.englishpremierleague.core.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CustomApplication : MultiDexApplication() {
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CustomApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    helperModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}