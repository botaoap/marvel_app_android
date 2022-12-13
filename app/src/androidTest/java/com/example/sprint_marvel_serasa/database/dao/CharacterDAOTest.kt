package com.example.sprint_marvel_serasa.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.sprint_marvel_serasa.database.MarvelDatabase
import com.example.sprint_marvel_serasa.model.Results
import com.example.sprint_marvel_serasa.model.Thumbnail
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CharacterDAOTest {

    private lateinit var database: MarvelDatabase
    private lateinit var dao: CharacterDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MarvelDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = database.characterDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun get_all_heroes_return_true() {
        val thumbnail = Thumbnail(path = "urlLink", extension = "jpg")
        val character = Results(
            char_id = 1,
            name = "Super-Man",
            description = "Forte",
            modified = "Nenhum",
            thumbnail = thumbnail,
            comics = null,
            comicSplitId = null
        )
        val list = mutableListOf<Results>()
        list.add(character)

        runBlocking {
            dao.insertHeroes(list)

            val result = dao.getAllHeroes()
            assertThat(result).hasSize(1)
        }

    }

    @Test
    fun insert_heroes_return_true() {
        val thumbnail = Thumbnail(path = "urlLink", extension = "jpg")
        val character = Results(
            char_id = 1,
            name = "Super-Man",
            description = "Forte",
            modified = "Nenhum",
            thumbnail = thumbnail,
            comics = null,
            comicSplitId = null
        )
        val character2 = Results(
            char_id = 2,
            name = "Super-Man",
            description = "Forte",
            modified = "Nenhum",
            thumbnail = thumbnail,
            comics = null,
            comicSplitId = null
        )
        val list = mutableListOf<Results>()

        list.add(character)
        list.add(character2)

        runBlocking {
            dao.insertHeroes(list)

            val result = dao.getAllHeroes()

            assertThat(result).hasSize(2)
        }
    }

    @Test
    fun update_heroes_return_true() {
        val thumbnail = Thumbnail(path = "urlLink", extension = "jpg")
        val character = Results(
            char_id = 1,
            name = "Super-Man",
            description = "Forte",
            modified = "Nenhum",
            thumbnail = thumbnail,
            comics = null,
            comicSplitId = null
        )
        val character2 = Results(
            char_id = 1,
            name = "Spider-Man",
            description = "Forte",
            modified = "Nenhum",
            thumbnail = thumbnail,
            comics = null,
            comicSplitId = null
        )
        val list = mutableListOf<Results>()

        list.add(character)
        list.add(character2)

        runBlocking {
            dao.insertHeroes(list)

            val update = dao.updateHeroes(character2)

            val result = dao.getAllHeroes()

            assertThat(result).isNotEqualTo(update)
        }
    }

    @Test
    fun delete_heroes_return_true() {
        val thumbnail = Thumbnail(path = "urlLink", extension = "jpg")
        val character = Results(
            char_id = 1,
            name = "Super-Man",
            description = "Forte",
            modified = "Nenhum",
            thumbnail = thumbnail,
            comics = null,
            comicSplitId = null
        )
        val list = mutableListOf<Results>()

        list.add(character)

        runBlocking {
            dao.insertHeroes(list)
            dao.deleteHeroes(character)

            val result = dao.getAllHeroes()

            assertThat(result).hasSize(0)
        }
    }

}