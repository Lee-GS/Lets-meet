package com.example.letsmeet.room.dao

import androidx.room.*
import com.example.letsmeet.room.entity.FriendData
import com.example.letsmeet.room.entity.PlanData

@Dao
interface FriendDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriend(friendData: FriendData)
    @Update
    suspend fun updateFriend(friendData: FriendData)
    @Delete
    suspend fun deleteFriend(friendData: FriendData)
    @Query("SELECT * FROM friends")
    suspend fun getAll(): List<FriendData>
}