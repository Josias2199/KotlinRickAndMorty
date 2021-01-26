package com.example.rickandmorty.data.remote

class ApiHelper(private val apiService: ApiService) {
    suspend fun getCharacterList(page: Int) = apiService.getCharacterList(page)
}