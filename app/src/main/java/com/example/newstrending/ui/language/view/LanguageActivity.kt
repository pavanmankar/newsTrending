package com.example.newstrending.ui.language.view

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
import com.example.newstrending.data.model.LanguageList
import com.example.newstrending.databinding.ActivityLanguageBinding
import com.example.newstrending.di.component.DaggerActivityComponent
import com.example.newstrending.di.module.ActivityModule
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.ui.language.viewmodel.LanguageViewModel
import com.example.newstrending.ui.newsource.view.SourceDetailActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, LanguageActivity::class.java)
        }
    }

    @Inject
    lateinit var adapter: LanguageListAdapter

    @Inject
    lateinit var languageViewModel: LanguageViewModel

    private lateinit var binding: ActivityLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        supportActionBar?.title = "Language"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setContentView(binding.root)
        injectDependency()
        getIntentAndFetchData()
        setupObserver()
        setupUI()
    }

    private fun getIntentAndFetchData() {

    }

    private fun setupUI() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )

        adapter.itemClickListener = {
            val intent = SourceDetailActivity.getIntent(this, "","News", it.code)
            startActivity(intent)
        }

        languageViewModel.fetchLanguageList(application)
    }


    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                languageViewModel.uiState.collect {
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
                            Toast.makeText(this@LanguageActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(languageList: List<LanguageList>) {
        adapter.addData(languageList)
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
            .activityModule(ActivityModule(this)).build().injectLanguageActivity(this)
    }
}