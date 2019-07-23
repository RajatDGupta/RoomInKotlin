package com.leonet.kotlintest.view.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonet.kotlintest.R
import com.leonet.kotlintest.adapter.NewsAdapter
import com.leonet.kotlintest.databinding.ActivityMainBinding
import com.leonet.kotlintest.model.Articles

class MainActivity : AppCompatActivity() {

   lateinit var binding:ActivityMainBinding
    lateinit var viewModel:MainActivityViewModel
    lateinit var adapter:NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /*p0*/ initDataBinding()
       /*than*/

        initViewModel()
        setUpRecyclerview()

    }



    private fun initDataBinding() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)


    }

    private fun initViewModel() {

        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]

        viewModel.observeArticlesList().observe(this,
            Observer<List<Articles>> {
                   if(it!=null) {
                       adapter.setNewsList(it)
                   }

            })

        viewModel.observeProgress().observe(this, Observer<Boolean> {

            if(it){
                binding.progress.visibility=View.VISIBLE
            }else{
                binding.progress.visibility=View.INVISIBLE
            }
        })

        viewModel.observeError().observe(this, Observer<Throwable> {

            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()

        })
    }

    private fun setUpRecyclerview() {
        adapter= NewsAdapter()
        binding.rvNews.layoutManager=LinearLayoutManager(this)
        binding.rvNews.adapter=adapter
    }



}
