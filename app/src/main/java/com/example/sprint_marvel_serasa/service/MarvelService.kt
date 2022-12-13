package com.example.sprint_marvel_serasa.service

import com.example.sprint_marvel_serasa.BuildConfig
import com.example.sprint_marvel_serasa.model.Character
import com.example.sprint_marvel_serasa.model.Results
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    @GET("/v1/public/characters")
    suspend fun fetchCharacters(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String = BuildConfig.API_PUBLIC_KEY,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 20,
        @Query("nameStartsWith") nameStartsWith: String? = null
    ): Response<Character>

    @GET("/v1/public/characters/{id}")
    suspend fun fetchCharacterId(
        @Path("id") id: Int,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String = BuildConfig.API_PUBLIC_KEY,
        @Query("hash") hash: String,
    ): Response<Character>

    @GET("/v1/public/characters")
    suspend fun fetchCharactersWithComics(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String = BuildConfig.API_PUBLIC_KEY,
        @Query("hash") hash: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20,
        @Query("comics") comics: Int
    ): Response<Character>

}