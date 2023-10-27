package com.example.newstrending.di.module

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newstrending.data.repository.CountryRepository
import com.example.newstrending.data.repository.LanguageRepository
import com.example.newstrending.data.repository.NewSourceRepository
import com.example.newstrending.data.repository.TopHeadlineRepository
import com.example.newstrending.di.ActivityContext
import com.example.newstrending.ui.base.ViewModelProviderFactory
import com.example.newstrending.ui.country.view.CountryListAdapter
import com.example.newstrending.ui.country.viewmodel.CountryViewModel
import com.example.newstrending.ui.home.viewmodel.HomeViewModel
import com.example.newstrending.ui.language.view.LanguageListAdapter
import com.example.newstrending.ui.language.viewmodel.LanguageViewModel
import com.example.newstrending.ui.newsource.view.NewsourceAdapter
import com.example.newstrending.ui.newsource.view.SourceHeadlineAdapter
import com.example.newstrending.ui.newsource.viewmodel.NewSourceViewModel
import com.example.newstrending.ui.search.viewmodel.SearchViewModel
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
    fun provideLanguageViewModel(languageRepository: LanguageRepository) : LanguageViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(LanguageViewModel::class){
                LanguageViewModel(languageRepository)
            })[LanguageViewModel::class.java]
    }

    @Provides
    fun provideSearchViewModel(topHeadlineRepository: TopHeadlineRepository) : SearchViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(SearchViewModel::class){
                SearchViewModel(topHeadlineRepository)
            })[SearchViewModel::class.java]
    }

    @Provides
    fun provideCountryViewModel(countryRepository: CountryRepository) : CountryViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(CountryViewModel::class){
                CountryViewModel(countryRepository)
            })[CountryViewModel::class.java]
    }


    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun providePagingTopHeadlineAdapter() = PagingTopHeadlineAdapter(ArrayList())

    @Provides
    fun provideNewSourceAdapter() = NewsourceAdapter(ArrayList())

    @Provides
    fun provideSourceHeadlineAdapter() = SourceHeadlineAdapter(ArrayList())

    @Provides
    fun provideCountryListAdapter() = CountryListAdapter(ArrayList())

    @Provides
    fun provideLanguageListAdapter() = LanguageListAdapter(ArrayList())


}