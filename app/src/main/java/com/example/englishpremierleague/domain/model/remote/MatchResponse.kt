package com.example.englishpremierleague.domain.model.remote

data class MatchResponse(
    val count: Int,
    val matches: List<Match>
)