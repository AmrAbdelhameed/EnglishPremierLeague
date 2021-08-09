package com.example.englishpremierleague.data.repositoryimpl

import com.example.englishpremierleague.data.source.remote.helper.ApiHelper
import com.example.englishpremierleague.domain.repository.MatchRepository

class MatchRepositoryImpl(private val apiHelper: ApiHelper) : MatchRepository {
    override suspend fun getMatchResponse() = apiHelper.getMatchResponse()
}