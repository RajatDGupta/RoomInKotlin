package com.leonet.kotlintest.api

import com.leonet.kotlintest.model.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface NewsApi {

    @GET("top-headlines?country=ar&apiKey=5a21f9b5859b4b0fbd27a109d0f26912")
    fun getNews():Observable<NewsResponse>

}