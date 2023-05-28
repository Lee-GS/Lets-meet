package com.example.letsmeet.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.letsmeet.room.dao.ContentDao
import com.example.letsmeet.room.entity.ContentData

@Database(entities = [ContentData::class], version = 1)
abstract class ContentDatabase : RoomDatabase(){
    abstract fun contentDao() : ContentDao
}