package com.example.englishpremierleague.data.source.remote.helper

import com.example.englishpremierleague.domain.entity.remote.MatchResponse

interface ApiHelper {
    suspend fun getMatchResponse(): MatchResponse
}