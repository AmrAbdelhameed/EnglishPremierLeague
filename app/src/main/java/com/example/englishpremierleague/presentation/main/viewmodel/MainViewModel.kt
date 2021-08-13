package com.example.englishpremierleague.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishpremierleague.core.extension.convertDateOnly
import com.example.englishpremierleague.core.extension.getDay
import com.example.englishpremierleague.core.extension.extractDateOnly
import com.example.englishpremierleague.core.util.Constants.Day.TODAY
import com.example.englishpremierleague.core.util.Constants.Day.TOMORROW
import com.example.englishpremierleague.domain.model.local.Match
import com.example.englishpremierleague.domain.usecase.MatchUseCase
import com.example.englishpremierleague.presentation.main.intent.MainIntent
import com.example.englishpremierleague.presentation.main.model.MatchDataItem
import com.example.englishpremierleague.presentation.main.util.FilterObject
import com.example.englishpremierleague.presentation.main.viewstate.MainState
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
    private var matchesList: MutableList<MatchDataItem> = mutableListOf()
    private val matchesId = Channel<List<Int>>(1)
    private var todayMatches: List<MatchDataItem> = arrayListOf()
    private var tomorrowMatches: List<MatchDataItem> = arrayListOf()

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

    private fun fetchFavMatches() {
        viewModelScope.launch {
            matchesId.send(matchUseCase.getFavMatches().map { it.matchId })
        }
    }

    fun fetchMatches() {
        viewModelScope.launch {
            for (ids in matchesId) {
                _state.value = MainState.Loading
                _state.value = try {
                    val tmpMatchesList = matchUseCase.getMatches().map {
                        MatchDataItem(
                            it.id,
                            it.homeTeam.name,
                            it.awayTeam.name,
                            it.status,
                            it.score,
                            it.utcDate,
                            ids.contains(it.id)
                        )
                    }.sortedBy { it.utcDate } as MutableList<MatchDataItem>
                    todayMatches = tmpMatchesList.filter { it.utcDate.getDay() == TODAY }
                    tomorrowMatches = tmpMatchesList.filter { it.utcDate.getDay() == TOMORROW }
                    matchesList = tmpMatchesList.filter {
                        it.utcDate.getDay() != TODAY
                    }.filter {
                            it.utcDate.getDay() != TOMORROW
                        } as MutableList<MatchDataItem>
                    if (tomorrowMatches.isNotEmpty()) {
                        val matchDataItem = tomorrowMatches[0]
                        matchDataItem.matches = tomorrowMatches
                        matchesList.add(0, matchDataItem)
                    }
                    if (todayMatches.isNotEmpty()) {
                        val matchDataItem = todayMatches[0]
                        matchDataItem.matches = todayMatches
                        matchesList.add(0, matchDataItem)
                    }
                    MainState.Success(setHeaderId(matchesList))
                } catch (e: Exception) {
                    MainState.Error(e.localizedMessage)
                }
            }
        }
    }

    private fun setHeaderId(matches: List<MatchDataItem>): List<MatchDataItem> {
        var firstHeaderId: Long = 0
        var firstDate: String = matches[0].utcDate.extractDateOnly()
        for (match in matches) {
            if (firstDate != match.utcDate.extractDateOnly()) {
                firstDate = match.utcDate.extractDateOnly()
                firstHeaderId++
            }
            match.headerId = firstHeaderId
        }
        return matches
    }

    fun filterData(filterDataItem: FilterObject) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                var tmpMatchesList = matchesList.filter {
                    it.utcDate.getDay() != TODAY
                }.filter { it.utcDate.getDay() != TOMORROW
                } as MutableList<MatchDataItem>

                tmpMatchesList = tmpMatchesList.filter {
                    if (filterDataItem.status != "all") it.status == filterDataItem.status else true
                }.filter {
                    if (filterDataItem.from != "from") it.utcDate.convertDateOnly().after(
                        filterDataItem.from.convertDateOnly()
                    ) else true
                }.filter {
                        if (filterDataItem.to != "to") it.utcDate.convertDateOnly().before(
                            filterDataItem.to.convertDateOnly()
                        ) else {
                            true
                        }
                    }.filter {
                        if (filterDataItem.fav) {
                            it.isFav == true
                        } else {
                            true
                        }
                    } as MutableList<MatchDataItem>

                if (tomorrowMatches.isNotEmpty()) {
                    val matchDataItem = tomorrowMatches[0]
                    matchDataItem.matches = tomorrowMatches
                    tmpMatchesList.add(0, matchDataItem)
                }
                if (todayMatches.isNotEmpty()) {
                    val matchDataItem = todayMatches[0]
                    matchDataItem.matches = todayMatches
                    tmpMatchesList.add(0, matchDataItem)
                }
                MainState.Success(tmpMatchesList)
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