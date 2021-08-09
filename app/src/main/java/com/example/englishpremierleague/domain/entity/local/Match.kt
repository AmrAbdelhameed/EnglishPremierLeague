package com.example.englishpremierleague.domain.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
class Match(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)