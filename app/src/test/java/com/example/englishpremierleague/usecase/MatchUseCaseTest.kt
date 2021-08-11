package com.example.englishpremierleague.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.englishpremierleague.base.BaseUnitTest
import com.example.englishpremierleague.di.configureTestAppComponent
import com.example.englishpremierleague.domain.usecase.MatchUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class MatchUseCaseTest : BaseUnitTest() {

    private val matchUseCase: MatchUseCase by inject()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun start() {
        super.setUp()
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun test_fav_matches_use_case_returns_expected_value() = runBlocking {
        val dataReceived = matchUseCase.getFavMatches()
        assertNotNull(dataReceived)
    }

    @Test
    fun test_matches_use_case_returns_expected_value() = runBlocking {
        mockNetworkResponseWithFileContent("success_resp_list.json", HttpURLConnection.HTTP_OK)
        val dataReceived = matchUseCase.getMatches()
        assertNotNull(dataReceived)
    }
}