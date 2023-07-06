package com.example.letsmeet.room.dao

import androidx.room.*
import com.example.letsmeet.room.entity.ContentData

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContents(contentData: ContentData)
    @Update

    suspend fun updateContents(contentData: ContentData)
    @Delete
    suspend fun deleteContents(contentData: ContentData)
}