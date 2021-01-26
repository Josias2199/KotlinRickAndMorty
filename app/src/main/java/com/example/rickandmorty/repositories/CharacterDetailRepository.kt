package com.example.rickandmorty.repositories

import com.example.rickandmorty.data.remote.ApiClient
import com.example.rickandmorty.data.remote.ApiService

class CharacterDetailRepository {
    private var apiService: ApiService = ApiClient.getRetrofit().create(ApiService::class.java)

    suspend fun getCharacterDetail(id: Int) = apiService.getCharacterDetail(id)
}