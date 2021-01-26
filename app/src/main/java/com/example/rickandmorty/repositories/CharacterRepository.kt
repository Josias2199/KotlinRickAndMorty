package com.example.rickandmorty.repositories

import com.example.rickandmorty.data.remote.ApiHelper

class CharacterRepository(private val apiHelper: ApiHelper) {
    suspend fun getCharacterList(page: Int) = apiHelper.getCharacterList(page)
}