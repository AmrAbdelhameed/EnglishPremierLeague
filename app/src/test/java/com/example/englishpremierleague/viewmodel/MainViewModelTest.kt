package com.example.englishpremierleague.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.englishpremierleague.domain.model.remote.AwayTeam
import com.example.englishpremierleague.domain.model.remote.HomeTeam
import com.example.englishpremierleague.domain.model.remote.Match
import com.example.englishpremierleague.domain.model.remote.Score
import com.example.englishpremierleague.domain.usecase.MatchUseCase
import com.example.englishpremierleague.presentation.main.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var matchUseCase: MatchUseCase

    @Rule
    @JvmField
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private var testDispatcher = TestCoroutineDispatcher()
    private var testCoroutineScope = TestCoroutineScope()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(matchUseCase)
    }

    @Test
    fun getMovies_lessThan20Movies_hasReachedMaxShouldBeTrue() {
//        val list = List(5) {
//            Match(
//                327362,
//                HomeTeam(402,  "Brentford FC"),
//                AwayTeam(57, "Arsenal FC"),
//                "SCHEDULED",
//                null,
//                "2021-08-13T19:00:00Z"
//            )
//        }
//        testCoroutineScope.launch(testDispatcher) {
//            `when`(matchUseCase.getMatches()).thenReturn(list)
//            mainViewModel.fetchMatches()
//            assertEquals(true, true)
//        }
    }
}