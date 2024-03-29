package com.example.letsmeet.room.dao

import androidx.room.*
import com.example.letsmeet.room.entity.ContentData
import com.example.letsmeet.room.entity.FriendData

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDate(contentData : ContentData)
    @Update
    suspend fun updateContents(contentData: ContentData)
    @Delete
    suspend fun deleteContents(contentData: ContentData)

    @Query("SELECT * FROM contents3")
    suspend fun getAll(): List<ContentData>
}