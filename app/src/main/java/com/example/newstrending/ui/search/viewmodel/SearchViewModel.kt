package com.example.newstrending.ui.search.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.newstrending.data.model.Article
import com.example.newstrending.data.repository.TopHeadlineRepository
import com.example.newstrending.ui.base.BaseViewModel
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.util.AppConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(private val topHeadlineRepository: TopHeadlineRepository) :
    BaseViewModel<List<*>>() {

    private val searchQuery = MutableStateFlow("")

    init {
        fetchHeadlineNewsBySearch()
    }

    fun searchNews(query: String) {
        searchQuery.value = query
    }

    fun fetchHeadlineNewsBySearch() {
        viewModelScope.launch {
            searchQuery.debounce(AppConstant.DEBOUNCE_TIMEOUT).filter {
                    if (it.isNotEmpty() && it.length >= 3) {
                        return@filter true
                    } else {
                        _data.value = UiState.Success(emptyList<Article>())
                        return@filter false
                    }
                }.distinctUntilChanged().flatMapLatest {
                    _data.value = UiState.Loading
                    return@flatMapLatest topHeadlineRepository.getTopHeadlines("IN", it)
                        .catch { error ->
                            _data.value = UiState.Error(error.toString())
                        }
                }.flowOn(Dispatchers.IO).collect {
                    _data.value = UiState.Success(it)
                }
        }
    }


}