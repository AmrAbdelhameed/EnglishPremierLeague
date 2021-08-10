package com.example.englishpremierleague.presentation.intent

sealed class MainIntent {
    object FetchMatches : MainIntent()
    object FetchFavMatches : MainIntent()
}