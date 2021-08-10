package com.example.englishpremierleague.data.source.local.helper

import com.example.englishpremierleague.domain.entity.local.Match

interface DatabaseHelper {
    suspend fun insertMatch(match: Match)
    suspend fun deleteMatch(match: Match)
    suspend fun getMatches(): List<Match>
}