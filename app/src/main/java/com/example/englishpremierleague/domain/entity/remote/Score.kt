package com.example.englishpremierleague.domain.entity.remote

data class Score(
    val duration: String,
    val extraTime: ExtraTime,
    val fullTime: FullTime,
    val halfTime: HalfTime,
    val penalties: Penalties,
    val winner: Any
)