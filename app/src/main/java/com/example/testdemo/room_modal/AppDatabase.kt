package com.example.testdemo.room_modal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testdemo.responce.NewsListResponse

@Database(entities = arrayOf(NewsListResponse::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}

