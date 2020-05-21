package com.example.testdemo.room_modal

import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.example.testdemo.responce.NewsListResponse
import androidx.room.OnConflictStrategy.REPLACE as REPLACE1

@Dao
public interface NewsDao {
    @Insert(onConflict = REPLACE1)
    fun insert(vararg news: NewsListResponse)



    @Update
    fun update(vararg news: NewsListResponse?)

    @Query("SELECT * FROM news")
    fun getContacts(): NewsListResponse


}