package com.example.newstrending.ui.language.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.newstrending.data.model.LanguageList
import com.example.newstrending.ui.base.BaseViewModel
import com.example.newstrending.ui.base.UiState
import com.example.newstrending.util.JsonUtils
import kotlinx.coroutines.launch

class LanguageViewModel : BaseViewModel<List<*>>() {

    fun fetchLanguageList(context: Context) {
        viewModelScope.launch {
            val person: List<LanguageList> =
                JsonUtils.loadJSONFromAsset(context, "languagelist.json")
            person.let {
                if (person.isEmpty()) {
                    _data.value = UiState.Error("Something went wrong")
                } else {
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
}