package com.example.newstrending.data.model

import com.google.gson.annotations.SerializedName

data class CountryList (
    @SerializedName("name")
    val name: String = "",
    @SerializedName("code")
    val code: String = "",

)