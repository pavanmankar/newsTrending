package com.example.newstrending.ui.topheadline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstrending.data.model.Article
import com.example.newstrending.data.repository.TopHeadlineRepository
import com.example.newstrending.ui.base.BaseViewModel
import com.example.newstrending.ui.base.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TopHeadlineViewModel(private val topHeadlineRepository: TopHeadlineRepository) : BaseViewModel<List<*>>() {


    fun getTopHeadlineData(countryCode: String) {
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadlines(countryCode,"")
                .flowOn(Dispatchers.IO)
                .catch { error ->
                     println("Exception $error")
                    _data.value = UiState.Error(error.toString())
                }
                .collect {
                    _data.value = UiState.Success(it)
                }
        }

//        viewModelScope.launch {
//            topHeadlineRepository.getTopHeadlinesFromDb()
//                .flowOn(Dispatchers.IO)
//                .catch {
//                    _data.value = UiState.Error("Something went wrong")
//                }
//                .collect{
//                    _data.value = UiState.Success(it)
//                }
//        }
    }

}