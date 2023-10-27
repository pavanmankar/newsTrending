package com.example.newstrending.ui.country.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.newstrending.data.repository.CountryRepository
import com.example.newstrending.ui.base.BaseViewModel
import com.example.newstrending.ui.base.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CountryViewModel(private val countryRepository: CountryRepository) :
    BaseViewModel<List<*>>() {

    fun fetchCountryList() {
        viewModelScope.launch {
            countryRepository.getCountryList().flowOn(Dispatchers.IO).catch { e ->
                _data.value = UiState.Error(e.toString())
            }.collect {
                it.sortedBy {
                    when (it.code) {
                        "IN" -> 0
                        "US" -> 1
                        "GB" -> 2
                        "AE" -> 3
                        else -> 4
                    }
                }
                _data.value = UiState.Success(it)
            }
        }
    }

}