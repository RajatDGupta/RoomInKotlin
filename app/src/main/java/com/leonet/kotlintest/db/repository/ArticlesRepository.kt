package com.leonet.kotlintest.db.repository

import androidx.lifecycle.LiveData
import com.leonet.kotlintest.db.dao.ArticleDao
import com.leonet.kotlintest.model.Articles

class ArticlesRepository(private val articleDao: ArticleDao) {

    val allArticles: LiveData<List<Articles>> = articleDao.getAllWord()

     fun insert(articles: List<Articles>) {
        articleDao.insert(articles)
     }

    fun deleteAll(){
        articleDao.deleteAll()
    }
}