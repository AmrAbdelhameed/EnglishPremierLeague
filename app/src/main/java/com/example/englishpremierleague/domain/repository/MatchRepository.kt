package com.example.englishpremierleague.domain.repository

import com.example.englishpremierleague.domain.entity.remote.MatchResponse

interface MatchRepository {
    suspend fun getMatchResponse(): MatchResponse
}