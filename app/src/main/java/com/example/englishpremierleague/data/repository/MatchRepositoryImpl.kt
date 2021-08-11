package com.example.englishpremierleague.data.repository

import com.example.englishpremierleague.data.source.local.DatabaseHelper
import com.example.englishpremierleague.data.source.remote.ApiHelper
import com.example.englishpremierleague.domain.model.local.Match
import com.example.englishpremierleague.domain.repository.MatchRepository

class MatchRepositoryImpl(private val apiHelper: ApiHelper, private val databaseHelper: DatabaseHelper) : MatchRepository {
    override suspend fun getMatchResponse() = apiHelper.getMatchResponse()

    override suspend fun favMatch(match: Match)= databaseHelper.insertMatch(match)

    override suspend fun unFavMatch(match: Match) = databaseHelper.deleteMatch(match)

    override suspend fun getFavMatches(): List<Match> = databaseHelper.getMatches()
}