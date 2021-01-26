package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.local.Character
import com.example.rickandmorty.data.local.CharacterResponse
import com.example.rickandmorty.data.local.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("character/")
    suspend fun getCharacterList(@Query("page") page: Int): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterDetail(@Path("id") id: Int): Character

    @GET
    suspend fun getCharacterEpisode(@Url url: String): Episode
}