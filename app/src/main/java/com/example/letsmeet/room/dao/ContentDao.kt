package com.example.letsmeet.room.dao

import androidx.room.*
import com.example.letsmeet.room.entity.ContentData

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContents(vararg contentData: ContentData)
    @Update
    suspend fun updateContents(vararg contentData: ContentData)
    @Delete
    suspend fun deleteContents(vararg contentData: ContentData)
}