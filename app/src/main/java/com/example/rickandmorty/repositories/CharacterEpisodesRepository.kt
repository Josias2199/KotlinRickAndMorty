package com.example.rickandmorty.repositories

import com.example.rickandmorty.data.remote.ApiClient
import com.example.rickandmorty.data.remote.ApiService

class CharacterEpisodesRepository {
    private val apiService: ApiService = ApiClient.getRetrofit().create(ApiService::class.java)

    suspend fun getCharacterEpisodes(url: String) = apiService.getCharacterEpisode(url)
}