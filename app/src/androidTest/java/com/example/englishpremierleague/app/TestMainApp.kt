package com.example.englishpremierleague.app

import com.example.englishpremierleague.CustomApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.module.Module

@ExperimentalCoroutinesApi
class TestMainApp : CustomApplication() {
    override fun provideDependency() = listOf<Module>()
}