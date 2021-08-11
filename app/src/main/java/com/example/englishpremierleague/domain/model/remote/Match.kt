package com.example.englishpremierleague.domain.model.remote

data class Match(
    val id: Int,
    val homeTeam: HomeTeam,
    val awayTeam: AwayTeam,
    val status: String,
    val score: Score,
    val utcDate: String
)