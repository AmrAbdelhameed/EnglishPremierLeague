package com.example.englishpremierleague.presentation.main.model

import com.example.englishpremierleague.core.extension.*
import com.example.englishpremierleague.domain.model.remote.Score
import com.example.englishpremierleague.core.util.Constants.MatchStatus.EXTRA_TIME
import com.example.englishpremierleague.core.util.Constants.MatchStatus.FINISHED
import com.example.englishpremierleague.core.util.Constants.MatchStatus.FULL_TIME
import com.example.englishpremierleague.core.util.Constants.MatchStatus.HALF_TIME
import com.example.englishpremierleague.core.util.Constants.MatchStatus.PENALTIES

data class MatchDataItem(
    val id: Int,
    val homeName: String,
    val awayName: String,
    val status: String,
    val score: Score,
    val utcDate: String,
    var isFav: Boolean,
    var headerId: Long = 0,
    var matches: List<MatchDataItem> = arrayListOf()
) {
    val scoreStr: String = when (status) {
        PENALTIES -> { "${score.penalties.homeTeam} : ${score.penalties.awayTeam}" }
        EXTRA_TIME -> { "${score.extraTime.homeTeam} : ${score.extraTime.awayTeam}" }
        HALF_TIME -> { "${score.halfTime.homeTeam} : ${score.halfTime.awayTeam}" }
        else -> { "${score.fullTime.homeTeam} : ${score.fullTime.awayTeam}" }
    }

    val formatDate: String = utcDate.getDay()

    val scoreOrTime: String = when (status) {
        HALF_TIME, FULL_TIME, EXTRA_TIME, PENALTIES, FINISHED -> { scoreStr }
        else -> { utcDate.extractTimeOnly() }
    }
}