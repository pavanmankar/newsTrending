package com.example.newstrending.data.database.entity

import androidx.room.ColumnInfo

data class DbSource(
    @ColumnInfo("source_id")
    val id: String?=null,

    @ColumnInfo("source_name")
    val name: String
)
