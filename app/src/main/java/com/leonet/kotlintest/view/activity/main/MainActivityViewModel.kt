package com.leonet.kotlintest.view.activity.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.leonet.kotlintest.api.NewsApi
import com.leonet.kotlintest.db.AppDatabase
import com.leonet.kotlintest.db.repository.ArticlesRepository
import com.leonet.kotlintest.model.Articles
import com.leonet.kotlintest.model.NewsResponse
import com.leonet.kotlintest.network.ApiClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableArticleList = MutableLiveData<Throwable>()
    private val mutableError = MutableLiveData<Throwable>()
    private val mutableProgress = MutableLiveData<Boolean>()
    private val repository: ArticlesRepository


    init {

        val articleDao = AppDatabase.getDatabase(application).articleDao()
        repository = ArticlesRepository(articleDao)

        mutableProgress.value = true
        fetchNews()

    }

    private fun fetchNews() {
        getNewsObservable()!!.subscribeWith(getNewsObserver())
    }


    private fun getNewsObservable(): Observable<NewsResponse>? {
        return ApiClient.getRetrofit().create(NewsApi::class.java).getNews().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getNewsObserver(): DisposableObserver<NewsResponse> {

        return object : DisposableObserver<NewsResponse>() {
            override fun onComplete() {
                mutableProgress.value = false
            }

            override fun onNext(news: NewsResponse) {

                val deleteThread = object : Thread() {
                    override fun run() {
                        super.run()
                        delete()
                    }
                }

                val insertThread = object : Thread() {
                    override fun run() {
                        super.run()
                        insert(news.articles)
                    }
                }

                deleteThread.start()
                try {
                    deleteThread.join()
                }catch (e:Exception){
                    e.printStackTrace()
                }finally {
                    insertThread.start()
                }

            }

            override fun onError(e: Throwable) {
                mutableProgress.value = false
                mutableError.value = e
            }

        }

    }


    fun insert(articles: List<Articles>) {
        repository.insert(articles)
    }

    fun delete() {
        repository.deleteAll()
    }

    fun observeArticlesList(): LiveData<List<Articles>> {
        return repository.allArticles
    }

    fun observeError(): MutableLiveData<Throwable> {
        return mutableError
    }

    fun observeProgress(): MutableLiveData<Boolean> {
        return mutableProgress
    }


}