package com.example.letsmeet.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.letsmeet.room.entity.PlanData

@Dao
interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlans(vararg plan: PlanData)
    @Update
    suspend fun updatePlans(vararg planData: PlanData)
    @Delete
    suspend fun deletePlans(vararg planData: PlanData)

}