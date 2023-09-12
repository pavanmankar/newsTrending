package com.example.newstrending.di.module

import android.content.Context
import com.example.newstrending.NewsTrendingApplication
import com.example.newstrending.data.api.NetworkService
import com.example.newstrending.di.ApplicationContext
import com.example.newstrending.di.BaseUrl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: NewsTrendingApplication) {

    @ApplicationContext
    @Provides
    fun getContext(): Context {
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gson: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gson)
            .build()
            .create(NetworkService::class.java)
    }


}