package com.example.newstrending.ui.language.view

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
import com.example.newstrending.data.model.LanguageList
import com.example.newstrending.databinding.ActivityLanguageBinding
import com.example.newstrending.di.component.ActivityComponent
import com.example.newstrending.ui.base.BaseActivity
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.ui.language.viewmodel.LanguageViewModel
import com.example.newstrending.ui.newsource.view.SourceDetailActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageActivity : BaseActivity<LanguageViewModel, ActivityLanguageBinding>() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, LanguageActivity::class.java)
        }
    }

    @Inject
    lateinit var adapter: LanguageListAdapter

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.injectLanguageActivity(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        setUpToolbar("Languages")
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
            val intent = SourceDetailActivity.getIntent(this, "", "News", it.code)
            startActivity(intent)
        }

        viewModel.fetchLanguageList(application)
        binding.eLayout.tryAgainBtn.setOnClickListener {
            viewModel.fetchLanguageList(application)
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityLanguageBinding {
        return ActivityLanguageBinding.inflate(inflater)
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
                                renderList(list as List<LanguageList>)
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

}