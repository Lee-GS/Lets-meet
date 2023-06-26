package com.example.letsmeet.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.letsmeet.room.dao.FriendDao
import com.example.letsmeet.room.entity.FriendData

@Database(entities = [FriendData::class], version = 1)
abstract class FriendDatabase : RoomDatabase(){
    abstract fun friendDao() : FriendDao

    companion object{
        private var instance: FriendDatabase? = null

        @Synchronized
        fun getInstance(context: Context): FriendDatabase?{
            if (instance == null){
                synchronized(FriendDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FriendDatabase::class.java,
                        "friend.db"
                    ).build()
                }
            }
            return instance
        }
    }
}