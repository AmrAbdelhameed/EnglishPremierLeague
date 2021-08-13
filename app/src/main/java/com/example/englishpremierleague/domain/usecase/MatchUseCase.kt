package com.example.englishpremierleague.domain.usecase

import com.example.englishpremierleague.domain.model.remote.Match
import com.example.englishpremierleague.domain.repository.MatchRepository

class MatchUseCase constructor(private val repository: MatchRepository) {
    suspend fun getMatches(): List<Match> = repository.getMatchList().matches

    suspend fun favMatch(match: com.example.englishpremierleague.domain.model.local.Match) = repository.favMatch(match)

    suspend fun unFavMatch(match: com.example.englishpremierleague.domain.model.local.Match) = repository.unFavMatch(match)

    suspend fun getFavMatches(): List<com.example.englishpremierleague.domain.model.local.Match> = repository.getFavMatches()
}