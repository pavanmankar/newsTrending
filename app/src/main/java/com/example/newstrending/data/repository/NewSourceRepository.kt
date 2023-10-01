package com.example.newstrending.data.repository

import com.example.newstrending.data.api.NetworkService
import com.example.newstrending.data.model.Article
import com.example.newstrending.data.model.NewSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewSourceRepository @Inject constructor(private val networkService: NetworkService) {

    fun getNewSources(category:String,languageCode:String): Flow<List<NewSource>> {
        return flow {
            emit(networkService.getNewSources(category,languageCode))
        }.map {
            it.sources
        }.flowOn(Dispatchers.Default)
    }
}