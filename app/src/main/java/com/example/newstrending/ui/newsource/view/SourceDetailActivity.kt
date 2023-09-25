package com.example.newstrending.ui.newsource.view

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
import com.example.newstrending.data.model.NewSource
import com.example.newstrending.databinding.ActivitySourceDetailBinding
import com.example.newstrending.di.component.DaggerActivityComponent
import com.example.newstrending.di.module.ActivityModule
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.ui.newsource.viewmodel.NewSourceViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SourceDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRAS_CATEGORY = "EXTRAS_CATEGORY"
        const val EXTRAS_SOURCE_NAME = "EXTRAS_SOURCE_NAME"
        fun getIntent(context: Context, category: String, sourceName: String): Intent {
            return Intent(context, SourceDetailActivity::class.java).apply {
                putExtra(EXTRAS_CATEGORY, category)
                putExtra(EXTRAS_SOURCE_NAME, sourceName)
            }
        }
    }

    lateinit var binding: ActivitySourceDetailBinding

    @Inject
    lateinit var newSourceViewModel: NewSourceViewModel

    @Inject
    lateinit var adapter: SourceHeadlineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySourceDetailBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setContentView(binding.root)
        injectDependency()
        getIntentAndFetchData()
        setUpUi()
        setupObserver()
    }

    private fun getIntentAndFetchData() {
        val category = intent.getStringExtra(EXTRAS_CATEGORY)
        val sourceName = intent.getStringExtra(EXTRAS_SOURCE_NAME)
        category?.let {
            newSourceViewModel.fetchNewSources(it)
        }
        sourceName?.let {
            supportActionBar?.title = it
        }
    }

    private fun setUpUi() {
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
                newSourceViewModel.uiState.collect {
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

    private fun injectDependency() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsTrendingApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().injectSourceDetailActivity(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}