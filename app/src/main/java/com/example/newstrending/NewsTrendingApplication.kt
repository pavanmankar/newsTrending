package com.example.newstrending

import android.app.Application
import com.example.newstrending.di.component.ApplicationComponent
import com.example.newstrending.di.component.DaggerApplicationComponent
import com.example.newstrending.di.module.ApplicationModule

class NewsTrendingApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDependency()
    }

    private fun initDependency() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}