package com.example.letsmeet.room.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.example.letsmeet.room.TypeConvert
import com.example.letsmeet.room.dao.ContentDao
import com.example.letsmeet.room.entity.ContentData

@Database(entities = [ContentData::class], version = 1)
@TypeConverters(TypeConvert::class)
abstract class ContentDatabase : RoomDatabase() {
        abstract fun contentDao(): ContentDao
        companion object {
            private var instance: ContentDatabase? = null

            @Synchronized
            fun getInstance(context: Context): ContentDatabase? {
                if (instance == null) {
                    synchronized(ContentDatabase::class) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            ContentDatabase::class.java,
                            "content3.db"
                        ).build()
                    }
                }
                return instance
            }
        }
    }