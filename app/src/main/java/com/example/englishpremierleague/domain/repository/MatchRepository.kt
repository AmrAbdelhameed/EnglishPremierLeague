package com.example.englishpremierleague.domain.repository

import com.example.englishpremierleague.domain.model.local.Match
import com.example.englishpremierleague.domain.model.remote.MatchList

interface MatchRepository {
    suspend fun getMatchList(): MatchList
    suspend fun favMatch(match: Match)
    suspend fun unFavMatch(match: Match)
    suspend fun getFavMatches(): List<Match>
}