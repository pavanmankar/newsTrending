package com.example.newstrending.data.api

import com.example.newstrending.BuildConfig
import com.example.newstrending.data.model.NewSourceResponse
import com.example.newstrending.data.model.TopHeadlineResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @Headers("X-Api-Key: ${BuildConfig.MY_API_KEY}")
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country : String) : TopHeadlineResponse

    @Headers("X-Api-Key: ${BuildConfig.MY_API_KEY}")
    @GET("top-headlines/sources")
    suspend fun getNewSources(@Query("category") category : String,@Query("language") language : String) : NewSourceResponse
}