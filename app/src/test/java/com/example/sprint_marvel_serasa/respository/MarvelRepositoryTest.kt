package com.example.sprint_marvel_serasa.respository

import android.content.Context
import com.example.sprint_marvel_serasa.database.dao.CharacterDAO
import com.example.sprint_marvel_serasa.model.Character
import com.example.sprint_marvel_serasa.repository.MarvelRepository
import com.example.sprint_marvel_serasa.service.MarvelService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MarvelRepositoryTest {

    private lateinit var marvelRepository: MarvelRepository

    @Mock
    private lateinit var dao: CharacterDAO

    @Mock
    private lateinit var context: Context

    @Mock
    lateinit var resonponseService: Response<Character>

    @Mock
    lateinit var service: MarvelService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        marvelRepository = MarvelRepository(service = service, dao = dao, context = context)
    }

    @Test
    fun `fetch user need to return success response`()  = runBlocking {
        val timeStemp = marvelRepository.generateTimeStamp()
        Mockito.`when`(resonponseService.isSuccessful).thenReturn(true)
        Mockito.`when`(service.fetchCharacters(ts = timeStemp, hash = marvelRepository.generateHashMD5(timeStemp), offset = 0))
            .thenReturn(resonponseService)
        assertThat(resonponseService.isSuccessful).isEqualTo(true)
    }

    @Test
    fun `fetch user need to return failure response`()  = runBlocking {
        val timeStemp = marvelRepository.generateTimeStamp()
        Mockito.`when`(resonponseService.isSuccessful).thenReturn(false)
        Mockito.`when`(service.fetchCharacters(ts = timeStemp, hash = marvelRepository.generateHashMD5(timeStemp), offset = 0))
            .thenReturn(resonponseService)
        assertThat(resonponseService.isSuccessful).isEqualTo(false)
    }
}