package com.example.englishpremierleague.di

import com.example.englishpremierleague.core.di.repositoryModule
import com.example.englishpremierleague.core.di.useCaseModule

fun generateTestAppComponent(baseApi: String) = listOf(
    configureNetworkForInstrumentationTest(baseApi),
    MockWebServerInstrumentationTest,
    useCaseModule,
    repositoryModule
)