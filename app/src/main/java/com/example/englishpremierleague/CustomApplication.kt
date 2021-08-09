package com.example.englishpremierleague

import androidx.multidex.MultiDexApplication
import com.example.englishpremierleague.data.repositoryimpl.MatchRepositoryImpl
import com.example.englishpremierleague.data.source.local.DatabaseBuilder
import com.example.englishpremierleague.data.source.remote.ApiBuilder
import com.example.englishpremierleague.data.source.remote.helper.ApiHelper
import com.example.englishpremierleague.data.source.remote.helper.ApiHelperImpl
import com.example.englishpremierleague.domain.interactor.MatchUseCase
import com.example.englishpremierleague.domain.repository.MatchRepository
import com.example.englishpremierleague.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@ExperimentalCoroutinesApi
class CustomApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        val myModule = module {
            single { ApiBuilder.Companion.apiService() }
            single { DatabaseBuilder.Companion.appDatabase(get()) }

            viewModel { MainViewModel(get()) }

            single<ApiHelper> { ApiHelperImpl(get()) }
            single<MatchRepository> { MatchRepositoryImpl(get()) }
            single { MatchUseCase(get()) }
        }

        startKoin {
            androidContext(this@CustomApplication)
            modules(listOf(myModule))
        }
    }
}