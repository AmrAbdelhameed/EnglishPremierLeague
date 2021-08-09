package com.example.englishpremierleague.data.source.local.dao

import androidx.room.*
import com.example.englishpremierleague.domain.entity.local.Match

@Dao
interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: Match)

    @Delete
    suspend fun delete(match: Match)

    @Query("SELECT * FROM matches")
    suspend fun getPhotos(): List<Match>
}