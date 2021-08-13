package com.example.englishpremierleague.data.source.remote

import com.example.englishpremierleague.domain.model.remote.MatchList

interface ApiHelper {
    suspend fun getMatchList(): MatchList
}