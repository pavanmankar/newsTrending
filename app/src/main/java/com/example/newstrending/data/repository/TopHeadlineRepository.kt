package com.example.newstrending.data.repository

import com.example.newstrending.data.api.NetworkService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {
}