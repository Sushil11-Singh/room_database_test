package com.example.testdemo.api

import com.example.testdemo.responce.NewsListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface RequestInterface {

    @GET("top-headlines?country=us&apiKey=8159b5475b00447d8a81a7a807dcb8d7")
    fun getNews(): Observable<NewsListResponse>

}
