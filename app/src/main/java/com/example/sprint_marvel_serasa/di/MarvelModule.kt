package com.example.sprint_marvel_serasa.di

import android.content.Context
import com.example.sprint_marvel_serasa.database.MarvelDatabase
import com.example.sprint_marvel_serasa.database.dao.CharacterDAO
import com.example.sprint_marvel_serasa.repository.MarvelRepository
import com.example.sprint_marvel_serasa.service.MarvelService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class MarvelModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesMarvelApi(retrofit: Retrofit): MarvelService =
        retrofit.create(MarvelService::class.java)

    @Provides
    fun providesDatabase(@ApplicationContext contex: Context): MarvelDatabase {
        return MarvelDatabase.getDatabase(contex)
    }

    @Provides
    fun providesCharacterDAO(database: MarvelDatabase): CharacterDAO = database.characterDAO()

    @Provides
    fun providesRepository(
        service: MarvelService,
        dao: CharacterDAO,
        @ApplicationContext contex: Context
    ): MarvelRepository = MarvelRepository(service, dao, contex)


}