package com.example.englishpremierleague.domain.interactor

import com.example.englishpremierleague.domain.entity.remote.Match
import com.example.englishpremierleague.domain.repository.MatchRepository

class MatchUseCase constructor(private val repository: MatchRepository) {
    suspend fun getMatches(): List<Match> = repository.getMatchResponse().matches

    suspend fun favMatch(match: com.example.englishpremierleague.domain.entity.local.Match) = repository.favMatch(match)

    suspend fun unFavMatch(match: com.example.englishpremierleague.domain.entity.local.Match) = repository.unFavMatch(match)

    suspend fun getFavMatches(): List<com.example.englishpremierleague.domain.entity.local.Match> = repository.getFavMatches()
}