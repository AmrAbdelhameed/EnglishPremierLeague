package com.example.englishpremierleague.domain.entity.remote

data class MatchResponse(
    val count: Int,
    val matches: List<Match>
)