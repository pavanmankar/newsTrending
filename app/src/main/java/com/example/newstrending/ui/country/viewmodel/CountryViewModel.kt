package com.example.newstrending.ui.country.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.newstrending.data.model.CountryList
import com.example.newstrending.ui.base.BaseViewModel
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.util.JsonUtils
import kotlinx.coroutines.launch

class CountryViewModel : BaseViewModel<List<*>>() {

    fun fetchCountryList(context: Context) {
        viewModelScope.launch {
            val person: List<CountryList> = JsonUtils.loadJSONFromAsset(context, "countrylist.json")
            person.let {
                if (person.isEmpty()) {
                    _data.value = UiState.Error("Something went wrong")
                } else {
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

}