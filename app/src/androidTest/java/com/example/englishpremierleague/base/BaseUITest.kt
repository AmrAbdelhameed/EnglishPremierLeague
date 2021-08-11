package com.example.englishpremierleague.base

import androidx.test.platform.app.InstrumentationRegistry
import com.example.englishpremierleague.BuildConfig
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.io.BufferedReader
import java.io.Reader

abstract class BaseUITest : KoinTest {

    private lateinit var mockServer: MockWebServer

    @Before
    open fun setUp(){
        startMockServer()
    }

    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName))
    )

    fun getJson(path : String) : String {
        var content: String = ""
            val testContext = InstrumentationRegistry.getInstrumentation().context
            val inputStream = testContext.assets.open(path)
            val reader = BufferedReader(inputStream.reader() as Reader?)
            reader.use {
                content = it.readText()
            }
        return content
    }

    private fun startMockServer(){
        mockServer = MockWebServer()
        mockServer.start()
    }

    fun getMockWebServerUrl() = mockServer.url(BuildConfig.BASE_URL).toString()

    private fun stopMockServer() {
        mockServer.shutdown()
    }

    @After
    open fun tearDown(){
        stopMockServer()
        stopKoin()
    }
}