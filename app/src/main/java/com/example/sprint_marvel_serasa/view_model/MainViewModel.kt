package com.example.sprint_marvel_serasa.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sprint_marvel_serasa.database.MarvelDatabase
import com.example.sprint_marvel_serasa.model.BookmarkResults
import com.example.sprint_marvel_serasa.model.ComicSplit
import com.example.sprint_marvel_serasa.model.Results
import com.example.sprint_marvel_serasa.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MarvelRepository,
    private val database: MarvelDatabase
) : ViewModel() {

    private val _characterVertical = MutableLiveData<List<Results>>()
    var characterVertical: LiveData<List<Results>> = _characterVertical

    private val _characterHorizontal = MutableLiveData<List<Results>>()
    var characterHorizontal: LiveData<List<Results>> = _characterHorizontal

    private val _loadMoreCharacter = MutableLiveData<List<Results>>()
    var loadMoreCharacter: LiveData<List<Results>> = _loadMoreCharacter

    private val _characterVerticalSearchable = MutableLiveData<List<Results>>()
    var characterVerticalSearchable: LiveData<List<Results>> = _characterVerticalSearchable


    fun fetchCharacterHorizontal(offset: Int, limit: Int) {
        viewModelScope.launch {
            repository.fetchCharactersWithComics(offset = offset, limit = limit)?.let { heroes ->
                appendOnListOfHero(heroes)
                database.characterDAO().insertHeroes(heroes)
                database.characterDAO().insertBookmark(appendListBookmark(heroes))
                _characterHorizontal.value = heroes
            }
        }
    }

    fun fetchCharacterVertical(offset: Int, limit: Int) {
        viewModelScope.launch {
            repository.fetchCharacter(offset = offset, limit = limit)?.let { heroes ->
                appendOnListOfHero(heroes)
                database.characterDAO().insertHeroes(heroes)
                _characterVertical.value = heroes
            }
        }
    }

    fun fetchLoadMoreCharacter(offset: Int, limit: Int, nameStartWith: String?) {
        viewModelScope.launch {
            repository.fetchCharacter(offset = offset, limit = limit, nameStartWith = nameStartWith)?.let { heroes ->
                appendOnListOfHero(heroes)
                database.characterDAO().insertHeroes(heroes)
                _loadMoreCharacter.value = heroes
            }
        }
    }

    fun fetchCharacterVerticalSearchable(offset: Int, limit: Int, nameStartWith: String?) {
        viewModelScope.launch {
            repository.fetchCharacter(offset = offset, limit = limit, nameStartWith = nameStartWith)?.let { heroes ->
                appendOnListOfHero(heroes)
                database.characterDAO().insertHeroes(heroes)
                _characterVerticalSearchable.value = heroes
            }
        }
    }

    private fun appendOnListOfHero(list: List<Results>) {
        list.forEach { character ->
            val listOfId = mutableListOf<ComicSplit>()
            character.comics?.items?.forEach { item ->
                item.resourceURI.split("/").let {
                    listOfId.add(ComicSplit(it[it.size - 1]))
                    character.comicSplitId = listOfId
                }
            }
        }
    }

    private fun appendListBookmark(list: List<Results>): MutableList<BookmarkResults> {
        val listBookmark = mutableListOf<BookmarkResults>()
        list.forEach {
            BookmarkResults(bookmark = it.char_id.toInt()).apply {
                listBookmark.add(this)
            }
        }
        return listBookmark
    }
}