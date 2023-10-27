package com.example.newstrending.ui.search.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newstrending.data.model.Article
import com.example.newstrending.databinding.ActivitySearchBinding
import com.example.newstrending.di.component.ActivityComponent
import com.example.newstrending.ui.base.BaseActivity
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.ui.search.viewmodel.SearchViewModel
import com.example.newstrending.ui.topheadline.view.TopHeadlineAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    private fun setUpListener() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.searchNews(p0!!)
                return true
            }
        })
        binding.eLayout.tryAgainBtn.setOnClickListener {
            viewModel.fetchHeadlineNewsBySearch()
        }
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.injectSearchActivity(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        setUpToolbar("Search")
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        setUpListener()
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(inflater)
    }

    override fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.eLayout.errorLayout.visibility = View.GONE
                            it.data?.let { list ->
                                renderList(list as List<Article>)
                            }
                        }
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.eLayout.errorLayout.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            binding.eLayout.errorLayout.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@SearchActivity, it.message, Toast.LENGTH_LONG)
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
}
