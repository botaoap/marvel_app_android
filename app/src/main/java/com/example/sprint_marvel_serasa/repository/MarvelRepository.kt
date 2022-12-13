package com.example.sprint_marvel_serasa.repository

import android.content.Context
import com.example.sprint_marvel_serasa.BuildConfig
import com.example.sprint_marvel_serasa.database.dao.CharacterDAO
import com.example.sprint_marvel_serasa.model.Results
import com.example.sprint_marvel_serasa.service.MarvelService
import com.example.sprint_marvel_serasa.utils.checkInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val service: MarvelService,
    private val dao: CharacterDAO,
    private val context: Context
) {
    fun generateTimeStamp(): String {
        return Timestamp(System.currentTimeMillis()).getTime().toString()
    }

    fun generateHashMD5(timeStamp: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(
            1, md.digest(
                (timeStamp + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY)
                    .toByteArray()
            )
        ).toString(16).padStart(32, '0')
    }

    suspend fun fetchCharacter(
        offset: Int,
        limit: Int,
        nameStartWith: String? = null
    ): List<Results>? {
        val timeStamp = generateTimeStamp()

        if (checkInternetConnection(context)) {
            return withContext(Dispatchers.Default) {
                service.fetchCharacters(
                    ts = timeStamp,
                    hash = generateHashMD5(timeStamp),
                    offset = offset,
                    limit = limit,
                    nameStartsWith = nameStartWith
                ).let {
                    processData(it)?.data?.results
                }
            }
        }
        return withContext(Dispatchers.Default) {
            if (nameStartWith.isNullOrEmpty()) dao.getAllHeroes() else dao.getHeroesFromDBByName(
                nameStartWith
            )
        }

    }

    suspend fun fetchCharacterId(id: Int): List<Results>? {
        val timeStamp = generateTimeStamp()

        if (checkInternetConnection(context)) {
            if (dao.getHeroesFromDBById(id).isNotEmpty()) {
                return dao.getHeroesFromDBById(id)
            }
            return withContext(Dispatchers.Default) {
                service.fetchCharacterId(id = id, ts = timeStamp, hash = generateHashMD5(timeStamp))
                    .let {
                        processData(it)?.data?.results
                    }
            }
        }
        return withContext(Dispatchers.Default) {
            dao.getHeroesFromDBById(id)
        }
    }

    suspend fun fetchCharactersWithComics(offset: Int, limit: Int): List<Results>? {
        val timeStamp = generateTimeStamp()
        if (checkInternetConnection(context)) {
            return withContext(Dispatchers.Default) {
                service.fetchCharactersWithComics(
                    ts = timeStamp,
                    hash = generateHashMD5(timeStamp),
                    offset = offset,
                    limit = limit,
                    comics = 1689
                ).let {
                    processData(it)?.data?.results
                }
            }
        }
        return withContext(Dispatchers.Default) {
            val listBookmark = mutableListOf<Results>()
            dao.getBookmarkHeroesFromDB().forEach {
                dao.getHeroesFromDBById(it).apply {
                    listBookmark.add(this[0])
                }
            }
            listBookmark
        }
    }

    private fun <T> processData(response: Response<T>): T? {
        return if (response.isSuccessful) response.body() else null
    }


}