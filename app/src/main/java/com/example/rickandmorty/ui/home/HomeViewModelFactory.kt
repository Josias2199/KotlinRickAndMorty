package com.example.rickandmorty.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.data.remote.ApiHelper
import com.example.rickandmorty.repositories.CharacterRepository

class HomeViewModelFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(CharacterRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}