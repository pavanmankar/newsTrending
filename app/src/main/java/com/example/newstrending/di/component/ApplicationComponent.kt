package com.example.newstrending.di.component

import android.content.Context
import com.example.newstrending.NewsTrendingApplication
import com.example.newstrending.data.api.NetworkService
import com.example.newstrending.data.repository.NewSourceRepository
import com.example.newstrending.data.repository.TopHeadlineRepository
import com.example.newstrending.di.ApplicationContext
import com.example.newstrending.di.module.ApplicationModule
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsTrendingApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getTopHeadlineRepository() : TopHeadlineRepository

    fun getNewSourceRepository() : NewSourceRepository


}