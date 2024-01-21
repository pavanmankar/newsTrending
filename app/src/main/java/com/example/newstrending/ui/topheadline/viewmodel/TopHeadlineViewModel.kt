package com.example.newstrending.ui.topheadline.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.newstrending.data.repository.TopHeadlineRepository
import com.example.newstrending.ui.base.BaseViewModel
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.util.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class TopHeadlineViewModel(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel<List<*>>() {


    fun getTopHeadlineData(countryCode: String) {

        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                topHeadlineRepository.getTopHeadlines(countryCode, "").flowOn(Dispatchers.IO)
                    .catch { error ->
                        println("Exception $error")
                        _data.value = UiState.Error(error.toString())
                    }.collect {
                        _data.value = UiState.Success(it)
                    }
            }
        } else {
            viewModelScope.launch {
                topHeadlineRepository.getTopHeadlinesFromDb().flowOn(Dispatchers.IO)
                    .catch { error ->
                        _data.value = UiState.Error("Something went wrong")
                    }.collect {
                        _data.value = UiState.Success(it)
                    }
            }
        }


    }

}