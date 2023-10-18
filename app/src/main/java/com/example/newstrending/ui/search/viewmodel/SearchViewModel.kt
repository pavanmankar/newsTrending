package com.example.newstrending.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstrending.data.model.Article
import com.example.newstrending.data.repository.TopHeadlineRepository
import com.example.newstrending.ui.base.BaseViewModel
import com.example.newstrending.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchViewModel(private val topHeadlineRepository: TopHeadlineRepository) : BaseViewModel<List<*>>() {

    fun fetchHeadlineNewsBySearch(query:String){
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadlines("IN",query)
                .catch { error ->
                    _data.value = UiState.Error(error.toString())
                }
                .collect {
                    _data.value = UiState.Success(it)
                }
        }
    }


}