package com.example.englishpremierleague

import androidx.multidex.MultiDexApplication
import com.example.englishpremierleague.core.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
open class CustomApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CustomApplication)
            modules(provideDependency())
        }
    }

    open fun provideDependency() = appComponent
}