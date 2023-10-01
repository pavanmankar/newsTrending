package com.example.newstrending.data.model

import com.google.gson.annotations.SerializedName

data class LanguageList(
    @SerializedName("name") val name: String = "",
    @SerializedName("code") val code: String = "",
    @SerializedName("nativeName") val nativeName: String = ""
    )
