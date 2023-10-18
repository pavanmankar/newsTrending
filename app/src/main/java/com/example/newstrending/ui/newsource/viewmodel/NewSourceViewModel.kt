package com.example.newstrending.ui.newsource.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstrending.data.model.NewSource
import com.example.newstrending.data.repository.NewSourceRepository
import com.example.newstrending.ui.base.BaseViewModel
import com.example.newstrending.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NewSourceViewModel(private val newSourceRepository: NewSourceRepository) : BaseViewModel<List<*>>() {

    fun fetchNewSources(category: String,langaueCode : String) {
        viewModelScope.launch {
            newSourceRepository.getNewSources(category,langaueCode)
                .catch {
                    _data.value = UiState.Error(it.toString())
                }
                .collect {
                    _data.value = UiState.Success(it)
                }
        }
    }

}