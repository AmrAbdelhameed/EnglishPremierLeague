package com.example.englishpremierleague.presentation.viewstate

import com.example.englishpremierleague.presentation.model.MatchDataItem

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class Success(val matches: List<MatchDataItem>) : MainState()
    data class Error(val error: String?) : MainState()

}