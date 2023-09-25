package com.example.newstrending.ui.country.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstrending.data.model.CountryList
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.util.JsonUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CountryList>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<CountryList>>> = _uiState

    fun fetchCountryList(context: Context) {
        viewModelScope.launch {
            val person: List<CountryList> = JsonUtils.loadJSONFromAsset(context, "countrylist.json")
            person.let {
                if (person.isEmpty()) {
                    _uiState.value = UiState.Error("Something went wrong")
                } else {
                  val list =  it.sortedBy {
                        when (it.code) {
                            "IN" -> 0
                            else -> 1
                        }
                    }
                    _uiState.value = UiState.Success(list)
                }
            }
        }
    }

}