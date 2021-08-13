package com.example.englishpremierleague.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.englishpremierleague.base.BaseUnitTest
import com.example.englishpremierleague.di.configureTestAppComponent
import com.example.englishpremierleague.domain.model.remote.MatchList
import com.example.englishpremierleague.domain.usecase.MatchUseCase
import com.example.englishpremierleague.presentation.main.model.MatchDataItem
import com.example.englishpremierleague.presentation.main.viewmodel.MainViewModel
import com.example.englishpremierleague.presentation.main.viewstate.MainState
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest : BaseUnitTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel

    @MockK
    lateinit var matchUseCase: MatchUseCase

    @Before
    fun start() {
        super.setUp()
        MockKAnnotations.init(this)
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun test_main_view_model_expected_value() {
        mainViewModel = MainViewModel(matchUseCase)

        val sampleResponse = getJson("matches.json")
        val jsonObj = Gson().fromJson(sampleResponse, MatchList::class.java)

        coEvery { matchUseCase.getMatches() } returns jsonObj.matches

        mainViewModel.fetchMatches()

        var matches: List<MatchDataItem>? = null
            runBlockingTest {
                mainViewModel.state.collect {
                    when (it) {
                        is MainState.Success -> {
                            matches = it.matches
                        }
                    }
                }
            }
        assertNotNull(matches)
    }
}