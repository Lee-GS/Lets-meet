package com.example.letsmeet.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.letsmeet.room.dao.ContentDao
import com.example.letsmeet.room.dao.PlanDao
import com.example.letsmeet.room.entity.ContentData
import com.example.letsmeet.room.entity.PlanData

@Database(entities = [PlanData::class], version = 1)
abstract class PlanDatabase: RoomDatabase() {
    abstract fun planDao() : PlanDao

    companion object{
        private var instance: PlanDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PlanDatabase?{
            if (instance == null){
                synchronized(PlanDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlanDatabase::class.java,
                        "plan.db"
                    ).build()
                }
            }
            return instance
        }
    }
}