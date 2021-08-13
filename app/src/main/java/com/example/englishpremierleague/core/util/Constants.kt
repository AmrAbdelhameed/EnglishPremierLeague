package com.example.englishpremierleague.core.util

import com.example.englishpremierleague.BuildConfig

object Constants {
    object Headers {
        const val X_AUTH_TOKEN = "X-Auth-Token"
    }

    object EndPoints {
        const val MATCHES = "v2/competitions/2021/matches"
    }

    object Database {
        const val NAME = "${BuildConfig.APPLICATION_ID}.db"
    }

    object MatchStatus{
        const val SCHEDULED = "SCHEDULED"
        const val HALF_TIME = "HALF-TIME"
        const val FULL_TIME = "FULL-TIME"
        const val EXTRA_TIME = "EXTRA-TIME"
        const val PENALTIES = "PENALTIES"
    }

    object Day{
        const val YESTERDAY = "Yesterday"
        const val TODAY = "Today"
        const val TOMORROW = "Tomorrow"
    }

    object MatchTypes{
        const val ITEM = 1
        const val LIST = 2
    }
}