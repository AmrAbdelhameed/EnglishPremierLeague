package com.example.englishpremierleague.presentation.model

import com.example.englishpremierleague.domain.entity.remote.Score
import com.example.englishpremierleague.utils.*
import com.example.englishpremierleague.utils.Constants.Day.TODAY
import com.example.englishpremierleague.utils.Constants.Day.TOMORROW
import com.example.englishpremierleague.utils.Constants.Day.YESTERDAY
import com.example.englishpremierleague.utils.Constants.MatchStatus.EXTRA_TIME
import com.example.englishpremierleague.utils.Constants.MatchStatus.FULL_TIME
import com.example.englishpremierleague.utils.Constants.MatchStatus.HALF_TIME
import com.example.englishpremierleague.utils.Constants.MatchStatus.PENALTIES

data class MatchDataItem(
    val id: Int,
    val homeName: String,
    val awayName: String,
    val status: String,
    val score: Score,
    val utcDate: String,
    var isFav: Boolean,
    var headerId: Long? = 0
) {
    val scoreStr: String = when (status) {
        Constants.MatchStatus.HALF_TIME -> {
            "${score.halfTime.homeTeam} : ${score.halfTime.awayTeam}"
        }
        Constants.MatchStatus.FULL_TIME -> {
            "${score.fullTime.homeTeam} : ${score.fullTime.awayTeam}"
        }
        Constants.MatchStatus.EXTRA_TIME -> {
            "${score.extraTime.homeTeam} : ${score.extraTime.awayTeam}"
        }
        else -> {
            "${score.penalties.homeTeam} : ${score.penalties.awayTeam}"
        }
    }

    val formatDate: String = when (compareDates(convertDate(utcDate))) {
        -1 -> YESTERDAY
        0 -> TODAY
        1 -> TOMORROW
        else -> extractDateOnly(utcDate)
    }

    val scoreOrTime: String = when (status) {
        HALF_TIME, FULL_TIME, EXTRA_TIME, PENALTIES -> { scoreStr }
        else -> { extractTimeOnly(utcDate) }
    }
}