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
import com.example.newstrending.ui.home.viewmodel.HomeViewModel
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
            val intent = Intent(this, TopHeadlineActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Top Headlines", Toast.LENGTH_SHORT).show()
        }

        binding.clNewsService.setOnClickListener {
            Toast.makeText(this, "News Service", Toast.LENGTH_SHORT).show()
        }

        binding.clCountries.setOnClickListener {
            Toast.makeText(this, "Countries", Toast.LENGTH_SHORT).show()
        }
    }

    private fun injectDependency() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsTrendingApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().injectHomeActivity(this)
    }
}