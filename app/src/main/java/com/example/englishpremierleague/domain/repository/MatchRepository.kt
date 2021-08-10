package com.example.englishpremierleague.domain.repository

import com.example.englishpremierleague.domain.entity.local.Match
import com.example.englishpremierleague.domain.entity.remote.MatchResponse

interface MatchRepository {
    suspend fun getMatchResponse(): MatchResponse
    suspend fun favMatch(match: Match)
    suspend fun unFavMatch(match: Match)
    suspend fun getFavMatches(): List<Match>
}