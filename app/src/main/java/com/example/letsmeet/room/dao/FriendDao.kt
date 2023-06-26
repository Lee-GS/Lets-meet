package com.example.letsmeet.room.dao

import androidx.room.*
import com.example.letsmeet.room.entity.FriendData
import com.example.letsmeet.room.entity.PlanData

@Dao
interface FriendDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriend(vararg friendData: FriendData)
    @Update
    suspend fun updateFriend(vararg friendData: FriendData)
    @Delete
    suspend fun deleteFriend(vararg friendData: FriendData)
}