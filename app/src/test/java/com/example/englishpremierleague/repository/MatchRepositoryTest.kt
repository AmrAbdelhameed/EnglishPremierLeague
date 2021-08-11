package com.example.englishpremierleague.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.englishpremierleague.base.BaseUnitTest
import com.example.englishpremierleague.di.configureTestAppComponent
import com.example.englishpremierleague.domain.repository.MatchRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class MatchRepositoryTest : BaseUnitTest(){

    private val matchRepository : MatchRepository by inject()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun start(){
        super.setUp()
        startKoin{ modules(configureTestAppComponent(getMockWebServerUrl()))}
    }

    @Test
    fun test_fav_matches_repository_returns_expected_value() =  runBlocking {
        val dataReceived = matchRepository.getFavMatches()
        assertNotNull(dataReceived)
    }

    @Test
    fun test_matches_repository_returns_expected_value() =  runBlocking {
        mockNetworkResponseWithFileContent("matches.json", HttpURLConnection.HTTP_OK)
        val dataReceived = matchRepository.getMatchResponse()
        assertNotNull(dataReceived)
    }
}