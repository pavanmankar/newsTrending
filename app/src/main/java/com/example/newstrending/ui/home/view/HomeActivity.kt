package com.example.newstrending.ui.home.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.newstrending.NewsTrendingApplication
import com.example.newstrending.R
import com.example.newstrending.databinding.ActivityMainBinding
import com.example.newstrending.di.component.DaggerActivityComponent
import com.example.newstrending.di.module.ActivityModule
import com.example.newstrending.ui.country.view.CountryListActivity
import com.example.newstrending.ui.home.viewmodel.HomeViewModel
import com.example.newstrending.ui.language.view.LanguageActivity
import com.example.newstrending.ui.newsource.view.NewSourceActivity
import com.example.newstrending.ui.search.view.SearchActivity
import com.example.newstrending.ui.topheadline.view.TopHeadlineActivity
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var homeViewModel: HomeViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        injectDependency()
        handleClickEvent()
    }

    private fun handleClickEvent() {
        binding.clTopHeadline.setOnClickListener {
            val intent = TopHeadlineActivity.getIntent(this,"IN")
            startActivity(intent)
        }

        binding.clNewsService.setOnClickListener {
            val intent = NewSourceActivity.getIntent(this)
            startActivity(intent)
        }

        binding.clCountries.setOnClickListener {
            val intent = CountryListActivity.getIntent(this)
            startActivity(intent)
        }

        binding.clLanguage.setOnClickListener {
            val intent = LanguageActivity.getIntent(this)
            startActivity(intent)
        }

        binding.clSearch.setOnClickListener {
            val intent = SearchActivity.getIntent(this)
            startActivity(intent)
        }
    }

    private fun injectDependency() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsTrendingApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().injectHomeActivity(this)
    }
}