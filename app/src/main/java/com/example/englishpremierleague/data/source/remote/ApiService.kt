package com.example.englishpremierleague.data.source.remote

import com.example.englishpremierleague.domain.entity.remote.MatchResponse
import com.example.englishpremierleague.utils.Constants
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.EndPoints.MATCHES)
    suspend fun getMatchResponse(): MatchResponse
}