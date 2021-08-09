package com.example.englishpremierleague.utils

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
}