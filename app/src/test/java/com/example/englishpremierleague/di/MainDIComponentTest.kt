package com.example.englishpremierleague.di

import com.example.englishpremierleague.core.di.repositoryModule
import com.example.englishpremierleague.core.di.useCaseModule

fun configureTestAppComponent(baseApi: String) = listOf(
    MockWebServerDIPTest,
    configureNetworkModuleForTest(baseApi),
    useCaseModule,
    repositoryModule
)