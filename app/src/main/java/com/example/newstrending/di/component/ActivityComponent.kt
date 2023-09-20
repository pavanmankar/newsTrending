package com.example.newstrending.di.component

import android.app.Activity
import com.example.newstrending.di.ActivityScope
import com.example.newstrending.di.module.ActivityModule
import com.example.newstrending.di.module.ApplicationModule
import com.example.newstrending.ui.home.view.HomeActivity
import com.example.newstrending.ui.home.viewmodel.HomeViewModel
import com.example.newstrending.ui.newsource.view.NewSourceActivity
import com.example.newstrending.ui.newsource.view.SourceDetailActivity
import com.example.newstrending.ui.topheadline.view.TopHeadlineActivity
import dagger.Component
import javax.inject.Singleton

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun injectHomeActivity(activity: HomeActivity)

    fun injectTopHeadlineActivity(activity: TopHeadlineActivity)

    fun injectNewSorceActivity(activity: NewSourceActivity)

    fun injectSourceDetailActivity(activity: SourceDetailActivity)


}