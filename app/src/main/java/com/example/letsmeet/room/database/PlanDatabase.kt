package com.example.letsmeet.room.database

import androidx.room.Database
import com.example.letsmeet.room.dao.ContentDao
import com.example.letsmeet.room.dao.PlanDao
import com.example.letsmeet.room.entity.ContentData
import com.example.letsmeet.room.entity.PlanData

@Database(entities = [PlanData::class], version = 1)
abstract class PlanDatabase {
    abstract fun planDao() : PlanDao
}