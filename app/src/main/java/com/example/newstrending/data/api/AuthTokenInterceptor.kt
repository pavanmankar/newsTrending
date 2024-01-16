package com.example.newstrending.data.api

import com.example.newstrending.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder =
            originalRequest.newBuilder().header("X-Api-Key", BuildConfig.MY_API_KEY)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}