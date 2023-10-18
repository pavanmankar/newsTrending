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
import com.example.newstrending.databinding.ActivityNewSourceBinding
import com.example.newstrending.di.component.ActivityComponent
import com.example.newstrending.ui.base.BaseActivity
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.ui.newsource.viewmodel.NewSourceViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewSourceActivity : BaseActivity<NewSourceViewModel, ActivityNewSourceBinding>() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, NewSourceActivity::class.java)
        }
    }

    @Inject
    lateinit var adapter: NewsourceAdapter

    override fun setupView(savedInstanceState: Bundle?) {
        setUpToolbar("Sources")
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

        adapter.itemClickListener = {
            val intent = SourceDetailActivity.getIntent(this, it.category, it.name, "")
            startActivity(intent)
        }
        viewModel.fetchNewSources("", "")
        binding.eLayout.tryAgainBtn.setOnClickListener {
            viewModel.fetchNewSources("", "")
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityNewSourceBinding {
        return ActivityNewSourceBinding.inflate(inflater)
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.injectNewSorceActivity(this)
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
                            Toast.makeText(this@NewSourceActivity, it.message, Toast.LENGTH_LONG)
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