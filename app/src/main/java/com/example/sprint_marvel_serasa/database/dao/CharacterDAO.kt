package com.example.sprint_marvel_serasa.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.sprint_marvel_serasa.model.BookmarkResults
import com.example.sprint_marvel_serasa.model.Results

@Dao
interface CharacterDAO {

    @Transaction
    @Query("SELECT * FROM Results")
    suspend fun getAllHeroes(): List<Results>

    @Transaction
    @Query("SELECT * FROM Results where char_name like '%' || :name || '%'")
    suspend fun getHeroesFromDBByName(name: String): List<Results>

    @Transaction
    @Query("SELECT * FROM Results where char_id = :id")
    suspend fun getHeroesFromDBById(id: Int): List<Results>

    @Transaction
    @Query("SELECT bookmark FROM BookmarkResults")
    suspend fun getBookmarkHeroesFromDB(): List<Int>

    @Insert(onConflict = REPLACE)
    suspend fun insertHeroes(results: List<Results>)

    @Insert(onConflict = REPLACE)
    suspend fun insertBookmark(bookmark: List<BookmarkResults>)

    @Delete
    suspend fun deleteHeroes(results: Results)

    @Update
    suspend fun updateHeroes(results: Results)
}