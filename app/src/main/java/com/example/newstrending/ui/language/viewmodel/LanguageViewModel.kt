package com.example.newstrending.ui.language.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.newstrending.data.repository.LanguageRepository
import com.example.newstrending.ui.base.BaseViewModel
import com.example.newstrending.ui.base.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LanguageViewModel(private val languageRepository: LanguageRepository) :
    BaseViewModel<List<*>>() {

    fun fetchLanguageList() {
        viewModelScope.launch {
            languageRepository.getLanguageList().flowOn(Dispatchers.IO).catch {
                _data.value = UiState.Error(it.toString())
            }.collect {
                it.sortedBy {
                    when (it.code) {
                        "en" -> 0
                        "he" -> 1
                        "mr" -> 2
                        else -> 4
                    }
                }
                _data.value = UiState.Success(it)
            }
        }
    }
}