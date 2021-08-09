package com.example.englishpremierleague.domain.interactor

import com.example.englishpremierleague.domain.entity.remote.Match
import com.example.englishpremierleague.domain.repository.MatchRepository

class MatchUseCase constructor(private val repository: MatchRepository) {
    suspend fun getMatches(): List<Match> = repository.getMatchResponse().matches
}