package com.example.englishpremierleague.data.source.local

import android.content.Context
import androidx.room.Room
import com.example.englishpremierleague.utils.Constants

object DatabaseBuilder {
    object Companion {
        fun appDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                Constants.Database.NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}