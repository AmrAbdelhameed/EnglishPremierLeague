package com.example.englishpremierleague.core.source.local.dao

import androidx.room.*
import com.example.englishpremierleague.domain.model.local.Match

@Dao
interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: Match)

    @Delete
    suspend fun delete(match: Match)

    @Query("SELECT * FROM matches")
    suspend fun getAll(): List<Match>
}