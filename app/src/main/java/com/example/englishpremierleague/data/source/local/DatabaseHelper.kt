package com.example.englishpremierleague.data.source.local

import com.example.englishpremierleague.domain.model.local.Match

interface DatabaseHelper {
    suspend fun insertMatch(match: Match)
    suspend fun deleteMatch(match: Match)
    suspend fun getMatches(): List<Match>
}