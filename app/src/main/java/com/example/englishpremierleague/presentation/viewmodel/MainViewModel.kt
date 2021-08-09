package com.example.englishpremierleague.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishpremierleague.domain.interactor.MatchUseCase
import com.example.englishpremierleague.presentation.intent.MainIntent
import com.example.englishpremierleague.presentation.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(
    private val matchUseCase: MatchUseCase
) : ViewModel() {
    val mainIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchMatches -> fetchMatches()
                }
            }
        }
    }

    private fun fetchMatches() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Success(matchUseCase.getMatches())
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }
}