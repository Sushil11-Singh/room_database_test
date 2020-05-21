package com.example.testdemo.room_modal

import androidx.room.TypeConverter
import com.example.testdemo.responce.NewsListResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    private val gson = Gson()
    private val type: Type =
        object : TypeToken<List<NewsListResponse.Article?>?>() {}.type

    @TypeConverter
    fun stringToNestedData(json: String?): List<NewsListResponse.Article?>? {
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun nestedDataToString(nestedData: List<NewsListResponse.Article?>?): String? {
        return gson.toJson(nestedData, type)
    }
}