package com.example.sprint_marvel_serasa.database

import android.content.Context
import androidx.room.*
import com.example.sprint_marvel_serasa.database.dao.CharacterDAO
import com.example.sprint_marvel_serasa.model.*


@Database ( entities = [Results::class, ComicSplit::class, BookmarkResults::class], version = 1)
@TypeConverters(Converter::class)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun characterDAO(): CharacterDAO

    companion object {

        fun getDatabase(context: Context): MarvelDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MarvelDatabase::class.java,
                "db_marvel_heroes"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}