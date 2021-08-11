package com.example.englishpremierleague.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
class Match(
    @PrimaryKey
    var matchId: Int,
    var homeTeam: String,
    var awayTeam: String,
    var status: String,
    var score: String,
    var utcDate: String
)