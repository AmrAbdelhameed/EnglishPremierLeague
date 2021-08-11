package com.example.englishpremierleague.data.source.local

import com.example.englishpremierleague.core.source.local.AppDatabase
import com.example.englishpremierleague.domain.model.local.Match

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun insertMatch(match: Match) = appDatabase.matchDao().insert(match)

    override suspend fun deleteMatch(match: Match) = appDatabase.matchDao().delete(match)

    override suspend fun getMatches(): List<Match> = appDatabase.matchDao().getAll()
}