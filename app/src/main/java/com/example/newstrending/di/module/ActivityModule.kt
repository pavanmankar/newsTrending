package com.example.newstrending.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newstrending.data.repository.NewSourceRepository
import com.example.newstrending.data.repository.TopHeadlineRepository
import com.example.newstrending.di.ActivityContext
import com.example.newstrending.ui.base.ViewModelProviderFactory
import com.example.newstrending.ui.home.viewmodel.HomeViewModel
import com.example.newstrending.ui.newsource.view.NewsourceAdapter
import com.example.newstrending.ui.newsource.view.SourceHeadlineAdapter
import com.example.newstrending.ui.newsource.viewmodel.NewSourceViewModel
import com.example.newstrending.ui.topheadline.view.PagingTopHeadlineAdapter
import com.example.newstrending.ui.topheadline.view.TopHeadlineAdapter
import com.example.newstrending.ui.topheadline.viewmodel.TopHeadlineViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity:AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext() : Context{
        return activity
    }

    @Provides
    fun provideHomeViewModel() : HomeViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(HomeViewModel::class) {
                HomeViewModel(activity)
            })[HomeViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlineViewModel(topHeadlineRepository: TopHeadlineRepository) : TopHeadlineViewModel{
        return ViewModelProvider(activity,
        ViewModelProviderFactory(TopHeadlineViewModel::class){
            TopHeadlineViewModel(topHeadlineRepository)
        })[TopHeadlineViewModel::class.java]
    }

    @Provides
    fun provideNewSourceViewModel(newSourceRepository: NewSourceRepository) : NewSourceViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(NewSourceViewModel::class){
                NewSourceViewModel(newSourceRepository)
            })[NewSourceViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun providePagingTopHeadlineAdapter() = PagingTopHeadlineAdapter(ArrayList())

    @Provides
    fun provideNewSourceAdapter() = NewsourceAdapter(ArrayList())

    @Provides
    fun provideSourceHeadlineAdapter() = SourceHeadlineAdapter(ArrayList())


}