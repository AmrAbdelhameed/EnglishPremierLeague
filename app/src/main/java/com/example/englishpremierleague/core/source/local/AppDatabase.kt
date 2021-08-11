package com.example.englishpremierleague.core.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.englishpremierleague.core.source.local.dao.MatchDao
import com.example.englishpremierleague.domain.model.local.Match

@Database(
    entities = [Match::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao
}