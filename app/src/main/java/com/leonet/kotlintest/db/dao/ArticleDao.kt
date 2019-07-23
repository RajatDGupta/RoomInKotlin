package com.leonet.kotlintest.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.leonet.kotlintest.model.Articles

@Dao
interface ArticleDao {

    @Query("SELECT * from article_table")
    fun getAllWord(): LiveData<List<Articles>>

    @Query("DELETE FROM article_table")
    fun deleteAll()

    @Insert()
    fun insert( articles: List<Articles>)


}