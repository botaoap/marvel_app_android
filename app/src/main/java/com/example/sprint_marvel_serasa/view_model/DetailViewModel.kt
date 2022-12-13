package com.example.sprint_marvel_serasa.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sprint_marvel_serasa.model.Results
import com.example.sprint_marvel_serasa.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MarvelRepository
) : ViewModel() {

    private val _characterDetail = MutableLiveData<List<Results>>()
    var characterDetail: LiveData<List<Results>> = _characterDetail

    fun fetchCharacterId(id: Int) {
        viewModelScope.launch {
            _characterDetail.value = repository.fetchCharacterId(id = id)
        }
    }
}