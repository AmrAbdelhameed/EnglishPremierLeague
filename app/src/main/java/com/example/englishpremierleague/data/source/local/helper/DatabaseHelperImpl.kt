package com.example.englishpremierleague.data.source.local.helper

import com.example.englishpremierleague.data.source.local.AppDatabase
import com.example.englishpremierleague.domain.entity.local.Match

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun insertMatch(match: Match) = appDatabase.matchDao().insert(match)

    override suspend fun deleteMatch(match: Match) = appDatabase.matchDao().delete(match)

    override suspend fun getMatches(): List<Match> = appDatabase.matchDao().getAll()
}