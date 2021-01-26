package com.example.rickandmorty.data.local

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
): Serializable