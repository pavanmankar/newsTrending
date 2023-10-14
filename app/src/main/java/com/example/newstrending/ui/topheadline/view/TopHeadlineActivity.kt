package com.example.newstrending.ui.topheadline.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newstrending.NewsTrendingApplication
import com.example.newstrending.data.model.Article
import com.example.newstrending.databinding.ActivityTopHeadlineBinding
import com.example.newstrending.di.component.DaggerActivityComponent
import com.example.newstrending.di.module.ActivityModule
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.ui.topheadline.viewmodel.TopHeadlineViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {

    companion object {
        const val EXTRAS_COUNTRY = "EXTRAS_COUNTRY"
        fun getIntent(context: Context, countryCode: String): Intent {
            return Intent(context, TopHeadlineActivity::class.java).apply {
                putExtra(EXTRAS_COUNTRY, countryCode)
            }
        }
    }

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    @Inject
    lateinit var pagingAdapter: PagingTopHeadlineAdapter

    @Inject
    lateinit var topHeadlineViewModel: TopHeadlineViewModel
    private lateinit var binding: ActivityTopHeadlineBinding
    lateinit var category : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        supportActionBar?.title = "Top Headlines"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setContentView(binding.root)
        injectDependency()
        getIntentAndFetchData()
        setupObserver()
        setupUI()
    }

    private fun getIntentAndFetchData() {
        category = intent.getStringExtra(EXTRAS_COUNTRY)!!
        category.let {
            topHeadlineViewModel.getTopHeadlineData(it)
        }
    }

    private fun setupUI() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        binding.eLayout.tryAgainBtn.setOnClickListener {
            if(!category.isEmpty()){
                topHeadlineViewModel.getTopHeadlineData(category)
            }
        }
    }


    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                topHeadlineViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.eLayout.errorLayout.visibility = View.GONE
                        }
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.eLayout.errorLayout.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.eLayout.errorLayout.visibility = View.VISIBLE
                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun injectDependency() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsTrendingApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().injectTopHeadlineActivity(this)
    }
}