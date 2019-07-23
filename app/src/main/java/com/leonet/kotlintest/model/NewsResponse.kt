package com.leonet.kotlintest.model

import androidx.room.ColumnInfo

import androidx.room.Entity

import androidx.room.PrimaryKey

import androidx.annotation.NonNull

class NewsResponse(
    val status: String,
    val totalResults: Number,
    val articles: List<Articles>
)

@Entity(tableName = "article_table")
class Articles(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "urlToImage") val urlToImage: String?,
    @ColumnInfo(name = "publishedAt") val publishedAt: String?,
    @ColumnInfo(name = "content") val content: String?
)

