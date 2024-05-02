package com.example.notes_app_lab1.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class New(
    @PrimaryKey() val title: String,
    val description: String?,
    val pubDate: String,
)