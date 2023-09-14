package com.example.newstrending.ui.topheadline.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newstrending.BuildConfig
import com.example.newstrending.NewsTrendingApplication
import com.example.newstrending.R
import com.example.newstrending.data.model.Article
import com.example.newstrending.databinding.ActivityTopHeadlineBinding
import com.example.newstrending.di.component.DaggerActivityComponent
import com.example.newstrending.di.module.ActivityModule
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.ui.topheadline.viewmodel.TopHeadlineViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    @Inject
    lateinit var pagingAdapter: PagingTopHeadlineAdapter

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
        setupObserver()
        setupUI()
    }

    private fun setupUI() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
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
                        }
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
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