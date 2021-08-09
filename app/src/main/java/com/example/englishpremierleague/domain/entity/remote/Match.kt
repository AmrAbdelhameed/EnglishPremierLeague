package com.example.englishpremierleague.domain.entity.remote

data class Match(
    val id: Int,
    val awayTeam: AwayTeam,
    val homeTeam: HomeTeam,
    val matchday: Int,
    val score: Score,
    val stage: String,
    val status: String,
    val utcDate: String
)