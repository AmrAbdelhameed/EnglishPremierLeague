package com.example.englishpremierleague.core.di

import com.example.englishpremierleague.data.repository.MatchRepositoryImpl
import com.example.englishpremierleague.core.source.local.DatabaseBuilder
import com.example.englishpremierleague.data.source.local.DatabaseHelper
import com.example.englishpremierleague.data.source.local.DatabaseHelperImpl
import com.example.englishpremierleague.core.source.remote.ApiBuilder
import com.example.englishpremierleague.data.source.remote.ApiHelper
import com.example.englishpremierleague.data.source.remote.ApiHelperImpl
import com.example.englishpremierleague.domain.repository.MatchRepository
import com.example.englishpremierleague.domain.usecase.MatchUseCase
import com.example.englishpremierleague.presentation.main.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { ApiBuilder.Companion.apiService() }
}

val databaseModule = module {
    single { DatabaseBuilder.Companion.appDatabase(get()) }
}

val helperModule = module {
    single<ApiHelper> { ApiHelperImpl(get()) }
    single<DatabaseHelper> { DatabaseHelperImpl(get()) }
}

val repositoryModule = module {
    single<MatchRepository> { MatchRepositoryImpl(get(), get()) }
}

val useCaseModule = module {
    single { MatchUseCase(get()) }
}

@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}