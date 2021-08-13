package com.example.englishpremierleague.core.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
val appComponent = listOf(
    networkModule,
    databaseModule,
    helperModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)