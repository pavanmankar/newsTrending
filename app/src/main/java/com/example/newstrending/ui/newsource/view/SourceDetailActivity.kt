package com.example.newstrending.ui.newsource.view

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newstrending.data.model.NewSource
import com.example.newstrending.databinding.ActivitySourceDetailBinding
import com.example.newstrending.di.component.ActivityComponent
import com.example.newstrending.ui.base.BaseActivity
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.ui.newsource.viewmodel.NewSourceViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SourceDetailActivity : BaseActivity<NewSourceViewModel, ActivitySourceDetailBinding>() {

    companion object {
        const val EXTRAS_CATEGORY = "EXTRAS_CATEGORY"
        const val EXTRAS_SOURCE_NAME = "EXTRAS_SOURCE_NAME"
        const val EXTRAS_LANGUAGE = "EXTRAS_LANGUAGE"
        fun getIntent(
            context: Context, category: String, sourceName: String, languageCode: String
        ): Intent {
            return Intent(context, SourceDetailActivity::class.java).apply {
                putExtra(EXTRAS_CATEGORY, category)
                putExtra(EXTRAS_SOURCE_NAME, sourceName)
                putExtra(EXTRAS_LANGUAGE, languageCode)
            }
        }
    }

    @Inject
    lateinit var adapter: SourceHeadlineAdapter

    lateinit var languageCode: String
    lateinit var category: String

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.injectSourceDetailActivity(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
        getIntentAndFetchData()
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivitySourceDetailBinding {
        return ActivitySourceDetailBinding.inflate(inflater)
    }

    private fun getIntentAndFetchData() {
        category = intent.getStringExtra(EXTRAS_CATEGORY)!!
        val sourceName = intent.getStringExtra(EXTRAS_SOURCE_NAME)
        languageCode = intent.getStringExtra(EXTRAS_LANGUAGE)!!
        callApi()
        sourceName?.let {
            setUpToolbar(it)
        }
        binding.eLayout.tryAgainBtn.setOnClickListener {
            callApi()
        }

    }

    private fun callApi() {
        category.let {
            if (!languageCode.isEmpty()) {
                viewModel.fetchNewSources(it, languageCode)
            } else {
                viewModel.fetchNewSources(it, "")
            }
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
                            it.data?.let { list ->
                                renderList(list as List<NewSource>)
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
                            Toast.makeText(this@SourceDetailActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(data: List<NewSource>) {
        adapter.addData(data)
        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}