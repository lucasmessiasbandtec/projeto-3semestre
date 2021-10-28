package com.example.app_go.utils

import android.app.Activity
import com.example.app_go.adapters.HomeAdapter
import com.example.app_go.models.request.ArticleRequest


object PostList {

    val list = mutableListOf<ArticleRequest>()

    val adapter = HomeAdapter(list)

    fun addToHomeRV(post : ArticleRequest){
        list.add(post)
        adapter.notifyItemInserted(0)
    }
}