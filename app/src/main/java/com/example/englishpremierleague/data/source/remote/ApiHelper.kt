package com.example.englishpremierleague.data.source.remote

import com.example.englishpremierleague.domain.model.remote.MatchResponse

interface ApiHelper {
    suspend fun getMatchResponse(): MatchResponse
}