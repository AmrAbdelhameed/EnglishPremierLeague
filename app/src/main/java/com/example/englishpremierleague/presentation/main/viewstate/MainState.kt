package com.example.englishpremierleague.presentation.main.viewstate

import com.example.englishpremierleague.presentation.main.model.MatchDataItem

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class Success(val matches: List<MatchDataItem>) : MainState()
    data class Error(val error: String?) : MainState()
}