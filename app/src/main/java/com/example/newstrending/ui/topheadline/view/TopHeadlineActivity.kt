package com.example.newstrending.ui.topheadline.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newstrending.data.model.Article
import com.example.newstrending.databinding.ActivityTopHeadlineBinding
import com.example.newstrending.di.component.ActivityComponent
import com.example.newstrending.ui.base.BaseActivity
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.ui.topheadline.viewmodel.TopHeadlineViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineActivity : BaseActivity<TopHeadlineViewModel, ActivityTopHeadlineBinding>() {

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

    lateinit var category: String

    override fun setupView(savedInstanceState: Bundle?) {
        setUpToolbar("Top Headlines")
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        binding.eLayout.tryAgainBtn.setOnClickListener {
            if (!category.isEmpty()) {
                viewModel.getTopHeadlineData(category)
            }
        }
        getIntentAndFetchData()
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityTopHeadlineBinding {
        return ActivityTopHeadlineBinding.inflate(inflater)
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.injectTopHeadlineActivity(this)
    }

    private fun getIntentAndFetchData() {
        category = intent.getStringExtra(EXTRAS_COUNTRY)!!
        category.let {
            viewModel.getTopHeadlineData(it)
        }
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
                            it.data?.let { newsList ->
                                renderList(newsList as List<Article>)
                            }
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


}