package com.example.newstrending.data.repository

import android.content.Context
import com.example.newstrending.data.api.NetworkService
import com.example.newstrending.data.model.CountryList
import com.example.newstrending.data.model.NewSource
import com.example.newstrending.di.ApplicationContext
import com.example.newstrending.util.JsonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(@ApplicationContext private val context: Context)  {

    fun getCountryList(): Flow<List<CountryList>> {
        return flow<List<CountryList>> {
            emit(JsonUtils.loadJSONFromAsset(context, "countrylist.json"))
        }.flowOn(Dispatchers.IO)
    }

}