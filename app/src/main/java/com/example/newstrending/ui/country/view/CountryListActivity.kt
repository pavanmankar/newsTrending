package com.example.newstrending.ui.country.view

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
import com.example.newstrending.data.model.CountryList
import com.example.newstrending.databinding.ActivityCountryListBinding
import com.example.newstrending.di.component.ActivityComponent
import com.example.newstrending.ui.base.BaseActivity
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.ui.country.viewmodel.CountryViewModel
import com.example.newstrending.ui.topheadline.view.TopHeadlineActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryListActivity : BaseActivity<CountryViewModel, ActivityCountryListBinding>() {


    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CountryListActivity::class.java)
        }
    }

    @Inject
    lateinit var adapter: CountryListAdapter

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.injectCountryListActivity(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        setUpToolbar("Countries")
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
            val intent = TopHeadlineActivity.getIntent(this, it.code)
            startActivity(intent)
        }

        viewModel.fetchCountryList(application)
        binding.eLayout.tryAgainBtn.setOnClickListener {
            viewModel.fetchCountryList(application)
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityCountryListBinding {
        return ActivityCountryListBinding.inflate(inflater)
    }

    override fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data as List<CountryList>)
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
                            Toast.makeText(this@CountryListActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(countryList: List<CountryList>) {
        adapter.addData(countryList)
        adapter.notifyDataSetChanged()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}