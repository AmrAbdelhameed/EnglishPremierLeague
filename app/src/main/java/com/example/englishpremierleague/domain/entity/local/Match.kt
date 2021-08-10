package com.example.englishpremierleague.domain.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.englishpremierleague.domain.entity.remote.AwayTeam
import com.example.englishpremierleague.domain.entity.remote.HomeTeam
import com.example.englishpremierleague.domain.entity.remote.Score

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