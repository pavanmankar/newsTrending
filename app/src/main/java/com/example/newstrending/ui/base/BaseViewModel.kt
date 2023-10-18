package com.example.newstrending.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T>() : ViewModel() {
    protected val _data = MutableStateFlow<UiState<T>>(UiState.Loading)

    val data: StateFlow<UiState<T>> = _data
}