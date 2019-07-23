package com.leonet.kotlintest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leonet.kotlintest.R
import com.leonet.kotlintest.databinding.ItemRowLayoutBinding
import com.leonet.kotlintest.model.Articles

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var listArticles = listOf<Articles>()

    fun setNewsList(list: List<Articles>) {
        this.listArticles = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_row_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return listArticles.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        holder.binding.tvAuthor.text= listArticles.get(position).author
        holder.binding.tvTitle.text= listArticles.get(position).title
        holder.binding.tvDesc.text= listArticles.get(position).description
        Glide.with(holder.binding.ivImage).load(listArticles.get(position).urlToImage).into(holder.binding.ivImage)

    }

    class NewsViewHolder(binding: ItemRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        var binding:ItemRowLayoutBinding = binding

    }

}