package com.example.newstrending.di.module

import android.content.Context
import com.example.newstrending.BuildConfig
import com.example.newstrending.NewsTrendingApplication
import com.example.newstrending.data.api.NetworkService
import com.example.newstrending.di.ApplicationContext
import com.example.newstrending.di.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    @Provides
    @Singleton
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        okHttp : OkHttpClient,
        gson: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gson)
            .client(okHttp)
            .build()
            .create(NetworkService::class.java)
    }
}