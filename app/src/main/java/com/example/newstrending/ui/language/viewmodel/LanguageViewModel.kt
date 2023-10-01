package com.example.newstrending.ui.language.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstrending.data.model.Article
import com.example.newstrending.data.model.CountryList
import com.example.newstrending.data.model.LanguageList
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.util.JsonUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LanguageViewModel : ViewModel() {


    private val _uiState = MutableStateFlow<UiState<List<LanguageList>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<LanguageList>>> = _uiState

    fun fetchLanguageList(context: Context) {
        viewModelScope.launch {
            val person: List<LanguageList> = JsonUtils.loadJSONFromAsset(context, "languagelist.json")
            person.let {
                if (person.isEmpty()) {
                    _uiState.value = UiState.Error("Something went wrong")
                } else {
                    val list =  it.sortedBy {
                        when (it.code) {
                            "en" -> 0
                            "he" -> 1
                            "mr" -> 2
                            else -> 4
                        }
                    }
                    _uiState.value = UiState.Success(list)
                }
            }
        }
    }
}