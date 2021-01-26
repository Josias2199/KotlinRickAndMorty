package com.example.rickandmorty.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.rickandmorty.repositories.CharacterDetailRepository
import com.example.rickandmorty.repositories.CharacterEpisodesRepository
import com.example.rickandmorty.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class DetailViewModel : ViewModel() {
    private val characterDetailRepository: CharacterDetailRepository = CharacterDetailRepository()
    private val characterEpisodesRepository: CharacterEpisodesRepository = CharacterEpisodesRepository()

    fun getCharacterDetail(id: Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = characterDetailRepository.getCharacterDetail(id)))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message.toString()))
        }
    }

    fun getCharacterEpisodes(url: String) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = characterEpisodesRepository.getCharacterEpisodes(url)))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message.toString()))
        }
    }
}