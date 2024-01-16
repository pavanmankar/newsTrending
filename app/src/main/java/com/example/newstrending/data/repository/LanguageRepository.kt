package com.example.newstrending.data.repository

import android.content.Context
import com.example.newstrending.data.model.CountryList
import com.example.newstrending.data.model.LanguageList
import com.example.newstrending.di.ApplicationContext
import com.example.newstrending.util.JsonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LanguageRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getLanguageList(): Flow<List<LanguageList>> {
        return flow<List<LanguageList>> {
            emit(JsonUtils.loadJSONFromAsset(context, "languagelist.json"))
        }.flowOn(Dispatchers.IO)
    }

}