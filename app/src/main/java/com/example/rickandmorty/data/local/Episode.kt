package com.example.rickandmorty.data.local

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Episode(
    @SerializedName("air_date")
    val air_date: String,
    @SerializedName("characters")
    val characters: List<Any>,
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
): Serializable