package com.example.newstrending.data.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        when (response.code()) {
            200 -> {
                Log.d("ApiResponse", response.body().toString())
            }
            400 -> {
                Log.d("ApiResponse", response.message())
            }
            401 -> {
                //Show UnauthorizedError Message
            }
            403 -> {
                //Show Forbidden Message
            }
            404 -> {
                //Show NotFound Message
            }
            // ... and so on
        }
        Log.d("ApiResponse", request.toString())
        return response
    }
}