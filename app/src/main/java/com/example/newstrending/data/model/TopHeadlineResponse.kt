package com.example.newstrending.data.model

import com.google.gson.annotations.SerializedName

data class TopHeadlineResponse(
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    @SerializedName("articles")
    val articles: List<Article> = ArrayList()
)
