package com.example.englishpremierleague.presentation.main.model

import com.example.englishpremierleague.core.extension.compareDates
import com.example.englishpremierleague.core.extension.convertDate
import com.example.englishpremierleague.core.extension.extractDateOnly
import com.example.englishpremierleague.core.extension.extractTimeOnly
import com.example.englishpremierleague.core.util.Constants
import com.example.englishpremierleague.domain.model.remote.Score
import com.example.englishpremierleague.core.util.Constants.Day.TODAY
import com.example.englishpremierleague.core.util.Constants.Day.TOMORROW
import com.example.englishpremierleague.core.util.Constants.Day.YESTERDAY
import com.example.englishpremierleague.core.util.Constants.MatchStatus.EXTRA_TIME
import com.example.englishpremierleague.core.util.Constants.MatchStatus.FULL_TIME
import com.example.englishpremierleague.core.util.Constants.MatchStatus.HALF_TIME
import com.example.englishpremierleague.core.util.Constants.MatchStatus.PENALTIES
import kotlin.time.ExperimentalTime

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
        HALF_TIME -> { "${score.halfTime.homeTeam} : ${score.halfTime.awayTeam}" }
        FULL_TIME -> { "${score.fullTime.homeTeam} : ${score.fullTime.awayTeam}" }
        EXTRA_TIME -> { "${score.extraTime.homeTeam} : ${score.extraTime.awayTeam}" }
        else -> { "${score.penalties.homeTeam} : ${score.penalties.awayTeam}" }
    }

    @ExperimentalTime
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