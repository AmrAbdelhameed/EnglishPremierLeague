package com.example.englishpremierleague.app

import com.example.englishpremierleague.CustomApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.module.Module
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class TestMainApp : CustomApplication() {
    override fun provideDependency() = listOf<Module>()
}