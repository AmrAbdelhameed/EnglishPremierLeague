package com.example.englishpremierleague.presentation.main.intent

sealed class MainIntent {
    object FetchMatches : MainIntent()
    object FetchFavMatches : MainIntent()
}