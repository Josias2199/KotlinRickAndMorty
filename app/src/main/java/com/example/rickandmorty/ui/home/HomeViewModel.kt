package com.example.rickandmorty.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.rickandmorty.repositories.CharacterRepository
import com.example.rickandmorty.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeViewModel(private val characterRepository: CharacterRepository) : ViewModel() {

    fun getCharacterList(page: Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = characterRepository.getCharacterList(page)))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message.toString()))
        }
    }
}