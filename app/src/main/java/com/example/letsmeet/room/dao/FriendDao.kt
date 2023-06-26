package com.example.letsmeet.room.dao

import androidx.room.*
import com.example.letsmeet.room.entity.FriendData
import com.example.letsmeet.room.entity.PlanData

@Dao
interface FriendDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriend(vararg name: String)
    @Update
    suspend fun updateFriend(vararg name: String)
    @Delete
    suspend fun deleteFriend(vararg name: String)
    @Query("SELECT * FROM friends")
    suspend fun getAll(): ArrayList<FriendData>
}