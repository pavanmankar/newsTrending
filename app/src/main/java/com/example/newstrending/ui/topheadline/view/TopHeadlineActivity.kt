package com.example.newstrending.ui.topheadline.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import com.example.newstrending.BuildConfig
import com.example.newstrending.NewsTrendingApplication
import com.example.newstrending.R
import com.example.newstrending.databinding.ActivityTopHeadlineBinding
import com.example.newstrending.di.component.DaggerActivityComponent
import com.example.newstrending.di.module.ActivityModule
import com.example.newstrending.ui.topheadline.viewmodel.TopHeadlineViewModel
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {

    @Inject
    lateinit var topHeadlineViewModel: TopHeadlineViewModel
    lateinit var binding: ActivityTopHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        supportActionBar?.title = "Top Headlines"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setContentView(binding.root)
        injectDependency()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private fun injectDependency() {
       DaggerActivityComponent.builder()
           .applicationComponent((application as NewsTrendingApplication).applicationComponent)
           .activityModule(ActivityModule(this)).build().injectTopHeadlineActivity(this)
    }
}