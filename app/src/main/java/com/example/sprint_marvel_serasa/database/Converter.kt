package com.example.sprint_marvel_serasa.database

import androidx.room.TypeConverter
import com.example.sprint_marvel_serasa.model.ComicSplit
import com.google.gson.Gson


class Converter {
    @TypeConverter
    fun fromStringComics(value: List<ComicSplit>?) = Gson().toJson(value)

    @TypeConverter
    fun fromArrayListComics(value: String?) = Gson().fromJson(value, Array<ComicSplit>::class.java)?.toList()

}