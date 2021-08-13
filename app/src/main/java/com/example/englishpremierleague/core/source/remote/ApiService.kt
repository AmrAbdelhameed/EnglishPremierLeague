package com.example.englishpremierleague.core.source.remote

import com.example.englishpremierleague.domain.model.remote.MatchList
import com.example.englishpremierleague.core.util.Constants
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.EndPoints.MATCHES)
    suspend fun getMatchList(): MatchList
}