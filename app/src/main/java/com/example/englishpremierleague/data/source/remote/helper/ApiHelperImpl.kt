package com.example.englishpremierleague.data.source.remote.helper

import com.example.englishpremierleague.data.source.remote.ApiService

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getMatchResponse() = apiService.getMatchResponse()
}