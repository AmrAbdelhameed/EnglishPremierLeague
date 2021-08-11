package com.example.englishpremierleague.data.source.remote

import com.example.englishpremierleague.core.source.remote.ApiService

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getMatchResponse() = apiService.getMatchResponse()
}