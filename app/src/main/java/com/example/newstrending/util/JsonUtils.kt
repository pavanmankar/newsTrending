package com.example.newstrending.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object JsonUtils {

    inline fun <reified T> loadJSONFromAsset(context: Context, fileName: String): List<T> {
        val json: String?
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.defaultCharset())
        } catch (e: IOException) {
            e.printStackTrace()
            return emptyList()
        }
        val gson = Gson()
        val listType = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(json, listType)
    }
}