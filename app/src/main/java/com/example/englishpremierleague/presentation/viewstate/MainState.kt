package com.example.englishpremierleague.presentation.viewstate

import com.example.englishpremierleague.domain.entity.remote.Match

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class Success(val matches: List<Match>) : MainState()
    data class Error(val error: String?) : MainState()

}