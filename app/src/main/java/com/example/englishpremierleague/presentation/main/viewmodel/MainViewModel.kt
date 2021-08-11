package com.example.englishpremierleague.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishpremierleague.domain.model.local.Match
import com.example.englishpremierleague.domain.usecase.MatchUseCase
import com.example.englishpremierleague.presentation.main.intent.MainIntent
import com.example.englishpremierleague.presentation.main.util.FilterObject
import com.example.englishpremierleague.presentation.main.model.MatchDataItem
import com.example.englishpremierleague.presentation.main.viewstate.MainState
import com.example.englishpremierleague.core.extension.convertDateOnly
import com.example.englishpremierleague.core.extension.extractDateOnly
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
    private var matchesList: List<MatchDataItem> = arrayListOf()
    private val matchesId = Channel<List<Int>>(1)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchMatches -> fetchMatches()
                    is MainIntent.FetchFavMatches -> fetchFavMatches()
                }
            }
        }
    }

    fun fetchFavMatches() {
        viewModelScope.launch {
            matchesId.send(matchUseCase.getFavMatches().map { it.matchId })
        }
    }

    fun fetchMatches() {
        viewModelScope.launch {
            for (ids in matchesId) {
                _state.value = MainState.Loading
                _state.value = try {
                    matchesList = matchUseCase.getMatches().map {
                        MatchDataItem(
                            it.id,
                            it.homeTeam.name,
                            it.awayTeam.name,
                            it.status,
                            it.score,
                            it.utcDate,
                            ids.contains(it.id)
                        )
                    }.sortedBy { it.utcDate }
                    MainState.Success(setHeaderId(matchesList))
                } catch (e: Exception) {
                    MainState.Error(e.localizedMessage)
                }
            }
        }
    }

    private fun setHeaderId(matches: List<MatchDataItem>) : List<MatchDataItem>{
        var firstHeaderId: Long = 0
        var firstDate: String = extractDateOnly(matches[0].utcDate)
        for (match in matches) {
            if (firstDate != extractDateOnly(match.utcDate)) {
                firstDate = extractDateOnly(match.utcDate)
                firstHeaderId++
            }
            match.headerId= firstHeaderId
        }
        return matches
    }

    fun filterData(filterDataItem: FilterObject) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Success(matchesList
                    .filter {
                        if (filterDataItem.status != "all") it.status == filterDataItem.status else true
                    }
                    .filter {
                        if (filterDataItem.from != "from") convertDateOnly(it.utcDate)?.after(
                            convertDateOnly(filterDataItem.from)
                        ) == true else true
                    }
                    .filter {
                        if (filterDataItem.to != "to") convertDateOnly(it.utcDate)?.before(
                            convertDateOnly(filterDataItem.to)
                        ) == true else {
                            true
                        }
                    }
                    .filter {
                        if (filterDataItem.fav) {
                            it.isFav == true
                        } else {
                            true
                        }
                    }
                )
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }

    fun favMatch(match: Match) {
        viewModelScope.launch {
            matchUseCase.favMatch(match)
        }
    }

    fun unFavMatch(match: Match) {
        viewModelScope.launch {
            matchUseCase.unFavMatch(match)
        }
    }
}